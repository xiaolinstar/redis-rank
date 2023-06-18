package org.xiaolin.redis.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.xiaolin.redis.domain.Book;
import org.xiaolin.redis.mapper.BookMapper;

/**
 * TODO: 需要考虑以下问题
 * 1. 并发场景下的，数据库一致性问题：使用MyBatisPlus的Version关键字实现版本号控制
 * 2. 如何保证MySQL和Redis的一致性
 * 3. 如何维护热榜，对于key的选定
 * @author xlxing
 * @Description
 * @create 2023/5/30
 */

@Component
@RequiredArgsConstructor
public class RedisZSet {
    private final RedisTemplate<String, Object> redisTemplate;
    private final BookMapper bookMapper;

    public boolean insertBook(Book book) {
        return false;
    }

    /**
     * 点击查看书籍信息
     * @param bookId 书籍编号
     * @return 查询的书籍信息
     */
    public Book getBook(Long bookId) {
        return null;
    }

}
