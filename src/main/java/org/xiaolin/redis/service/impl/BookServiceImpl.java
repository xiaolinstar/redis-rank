package org.xiaolin.redis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.xiaolin.redis.common.constant.RedisConstant;
import org.xiaolin.redis.common.req.BookAddDto;
import org.xiaolin.redis.common.req.BookUpdateDto;
import org.xiaolin.redis.common.resp.R;
import org.xiaolin.redis.domain.Book;
import org.xiaolin.redis.exception.GlobalException;
import org.xiaolin.redis.service.BookService;
import org.xiaolin.redis.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
* @author xlxing
* @description 针对表【book(书籍)】的数据库操作Service实现
* @createDate 2023-05-31 14:36:39
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService{

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 查看数据信息
     * @param bookId 书籍主键
     * @return 书籍相关信息
     */
    @Override
    public R<Book> getBook(Long bookId) {
        // 读数据库操作
        Book book = this.getById(bookId);
        if (book == null) {
            throw new GlobalException("Book not found: "+bookId);
        } else {
            // 总的排行榜
            String sortKey = RedisConstant.getSortKey();
            BoundZSetOperations<String, Object> zSetSort = redisTemplate.boundZSetOps(sortKey);
            zSetSort.incrementScore(bookId, 1.0);

            // 当前小时内的排行榜
            String hourSortKey = RedisConstant.getSortHourKey();
            BoundZSetOperations<String, Object> zSetHour = redisTemplate.boundZSetOps(hourSortKey);
            zSetHour.incrementScore(bookId, 1.0);

            // 当天的排行榜
            String daySortKey = RedisConstant.getSortDayKey();
            BoundZSetOperations<String, Object> zSetDay = redisTemplate.boundZSetOps(daySortKey);
            zSetDay.incrementScore(bookId, 1.0);

            // Smooth排行榜
            String smoothSortKey = RedisConstant.getSmoothSortKey();
            BoundZSetOperations<String, Object> zSetSmooth = redisTemplate.boundZSetOps(smoothSortKey);
            Duration duration = Duration.between(RedisConstant.getSmoothSortDateTime(), LocalDateTime.now());
            zSetSmooth.incrementScore(bookId, duration.getSeconds() * RedisConstant.BETA);

            return R.ok(book);
        }
    }

    @Override
    public R<Page<Book>> getBookPage(int page, int pageSize) {
        Page<Book> bookInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Book::getUpdateTime);
        this.page(bookInfo, queryWrapper);
        return R.ok(bookInfo);
    }

    /**
     * 获取排行榜
     * @param start 开始索引
     * @param end 结束索引
     * @return 排行榜清单
     */
    @Override
    public R<List<Book>> rankDayBook(int start, int end) {
        return rank(start, end, RedisConstant.getSortDayKey());
    }

    @Override
    public R<List<Book>> rankHourBook(Integer start, Integer end) {
        return rank(start, end, RedisConstant.getSortHourKey());
    }

    @Override
    public R<List<Book>> rankBook(Integer start, Integer end) {
        return rank(start, end, RedisConstant.getSortKey());
    }

    @Override
    public R<List<Book>> rankSmoothBook(Integer start, Integer end) {
        return rank(start, end, RedisConstant.getSmoothSortKey());
    }


    private R<List<Book>> rank(Integer start, Integer end, String sortKey) {
        BoundZSetOperations<String, Object> operations = redisTemplate.boundZSetOps(sortKey);

        Set<Object> rankObjects = operations.reverseRange(start, end);
        if (rankObjects == null) {
            throw new GlobalException("查询排行榜失败");
        }
        List<Book> books = rankObjects
                .stream()
                .map(o -> this.getById(Long.valueOf(o.toString())))
                .toList();
        return R.ok(books);
    }

    @Override
    public R<Void> addBook(BookAddDto bookAddDto) {
        Book book = new Book(bookAddDto.getAuthorId(), bookAddDto.getName());
        boolean inserted = this.save(book);
        if (inserted) {
            return R.ok();
        } else {
            throw new GlobalException("书籍插入失败");
        }
    }


    @Override
    public R<Void> deleteBook(Long bookId) {
        String daySortKey = RedisConstant.getSortDayKey();
        String hourSortKey = RedisConstant.getSortHourKey();
        String sortKey = RedisConstant.getSortKey();
        String smoothSortKey = RedisConstant.getSmoothSortKey();

        // 删除redis缓存
        redisTemplate.opsForZSet().remove(daySortKey, bookId);
        redisTemplate.opsForZSet().remove(hourSortKey, bookId);
        redisTemplate.opsForZSet().remove(sortKey, bookId);
        redisTemplate.opsForZSet().remove(smoothSortKey, bookId);

        // 删除数据库
        boolean b = this.removeById(bookId);
        if (b) {
            return R.ok();
        } else {
            throw new GlobalException("用户不存在，删除操作失败");
        }
    }

    @Override
    public R<Void> updateBook(BookUpdateDto bookUpdateDto) {
        LambdaUpdateWrapper<Book> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Book::getId, bookUpdateDto.getId())
                .set(Book::getName, bookUpdateDto.getName())
                .set(Book::getAuthorId, bookUpdateDto.getAuthorId())
                .set(Book::getUpdateTime, LocalDateTime.now());
        boolean updated = this.update(updateWrapper);
        if (updated) {
            return R.ok();
        } else {
            throw new GlobalException("用户不存在");
        }
    }

    /**
     * 每天的02:01:10执行该定时任务
     * 1. 持久化Redis中的点击量到数据库中
     * 2. 清除3种无效的ZSet
     */
    @Override
    @Scheduled(cron = "10 1 2 * * ? *")
    @Transactional
    public void updateDatabase() {
        log.info("持久化Redis数据到MySQL中");
        String sortKey = RedisConstant.getSortKey();
//        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().rangeWithScores(sortKey, 0, -1);
        List<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().randomMembersWithScore(sortKey, 100L);
        if (tuples == null) {
            return;
        }

        List<Book> books = tuples
                .stream()
                .filter(tuple -> tuple.getValue() != null)
                .map(objectTypedTuple -> {
                    Long id = Long.valueOf(Objects.requireNonNull(objectTypedTuple.getValue()).toString());
                    int clickNum = Objects.requireNonNull(objectTypedTuple.getScore()).intValue();
                    Book book = this.getById(id);
                    book.setClickNum(clickNum);
                    return book;
                }).toList();

        this.updateBatchById(books);

        // 清除sortDayKey
        String lastSortDayKey = RedisConstant.getLastSortDayKey();
        Boolean deleted = redisTemplate.delete(lastSortDayKey);
        if (Boolean.TRUE.equals(deleted)) {
            log.info("清除前一天的排行榜成功");
        } else {
            log.info("清除前一天的排行榜失败");
        }

        // 清除sortHourKey
        for(int i=1; i<=24; i++) {
            String sortHourKey = RedisConstant.SORT_HOUR_PREFIX + LocalDateTime.now().minusHours(i).format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
            Boolean deletedSortHourKey = redisTemplate.delete(sortHourKey);
            if (Boolean.FALSE.equals(deletedSortHourKey)) {
                log.info("清除"+sortHourKey+"失败");
            }
        }

        // 清除sortSmoothKey
        String lastSmoothSortKey = RedisConstant.getLastSmoothSortKey();
        Boolean deletedSmooth = redisTemplate.delete(lastSmoothSortKey);
        if (Boolean.TRUE.equals(deletedSmooth)) {
            log.info("清除Smooth排行榜成功");
        } else {
            log.info("清除Smooth排行榜失败");
        }
    }

}




