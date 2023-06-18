package org.xiaolin.redis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.xiaolin.redis.common.constant.RedisConstant;
import org.xiaolin.redis.common.resp.R;
import org.xiaolin.redis.domain.Book;
import org.xiaolin.redis.service.BookService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author xlxing
 * @Description
 * @create 2023/5/30
 */
@SpringBootTest
public class BookTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private BookService bookService;

    @Test
    public void testAddBooks() {
        String daySortKey = RedisConstant.getSortDayKey();
        BoundZSetOperations<String, Object> zSetOperations = redisTemplate.boundZSetOps(daySortKey);

        for (int i=0; i<30; i++) {
            var book = new Book();
            book.setCreateTime(LocalDateTime.now());
            book.setAuthorId((long) i);
            book.setUpdateTime(LocalDateTime.now());
            book.setName("Book:"+i);
            book.setClickNum(0);
            book.setCommentNum(0);
            book.setLikeNum(0);
            bookService.save(book);
        }
        List<Book> books = bookService.list();
        // 点击量作为score
        books.forEach(b -> zSetOperations.add(b.getId(), b.getClickNum()));
        Set<Object> objects = zSetOperations.range(1, 5);
        assert objects != null;
        for (Object o : objects) {
            Book book = bookService.getById(o.toString());
            System.out.println(book.toString());
        }
    }

    @Test
    public void testSelectList() {
        String daySortKey = RedisConstant.SORT_DAY_PREFIX + LocalDate.now().toString().replace("-", "");
        BoundZSetOperations<String, Object> zSetOperations = redisTemplate.boundZSetOps(daySortKey);

        Random random = new Random();
        for(int i=0; i<200; i++) {
            zSetOperations.incrementScore(random.nextInt(30),1L);
        }

        Set<Object> objects = zSetOperations.range(0, 10);
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<Book>();
        queryWrapper.in(Book::getId, objects);
        List<Book> books = bookService.list(queryWrapper);
        books.forEach(b -> System.out.println(b.toString()));
    }

    @Test
    public void testBookRank() {
        Random random = new Random();
        for (int i=0; i<100; i++) {
            bookService.getBook(random.nextLong(1,30));
        }

        R<List<Book>> books = bookService.rankDayBook(0, 5);
        books.getData().forEach(b -> System.out.println(b.toString()));
    }

    @Test
    public void testLocalDateTime() {
        var now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        System.out.println(now);
        Duration duration = Duration.between(RedisConstant.getSmoothSortDateTime(), LocalDateTime.now());
        System.out.println(duration.getSeconds());
    }

}
