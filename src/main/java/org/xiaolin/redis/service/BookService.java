package org.xiaolin.redis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.xiaolin.redis.common.req.BookAddDto;
import org.xiaolin.redis.common.req.BookUpdateDto;
import org.xiaolin.redis.common.resp.R;
import org.xiaolin.redis.domain.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author xlxing
* @description 针对表【book(书籍)】的数据库操作Service
* @createDate 2023-05-31 14:36:39
*/
public interface BookService extends IService<Book> {
    /**
     * 增添书籍
     * @param bookAddDto 新增书籍
     * @return 插入状态
     */
    R<Void> addBook(BookAddDto bookAddDto);

    /**
     * 删除书籍
     * @param bookId 书籍id
     * @return 删除状态
     */
    R<Void> deleteBook(Long bookId);


    /**
     * 更新书籍
     * @param bookUpdateDto 书籍更新信息
     * @return 更新消息
     */
    R<Void> updateBook(BookUpdateDto bookUpdateDto);

    /**
     * 查看数据信息
     * @param bookId 书籍主键
     * @return 书籍相关信息
     */
    R<Book> getBook(Long bookId);

    /**
     * 分页获取书籍列表
     * @param page 页数
     * @param pageSize 页大小
     * @return 分页查询结果
     */
    R<Page<Book>> getBookPage(int page, int pageSize);
    /**
     *
     * @param start 开始索引
     * @param end 结束索引
     * @return 日排行榜内的书籍
     */
    R<List<Book>> rankDayBook(int start, int end);

    /**
     * 小时排行榜
     * @param start 开始索引
     * @param end 结束索引
     * @return 小时排行榜书籍
     */
    R<List<Book>> rankHourBook(Integer start, Integer end);


    /**
     * 总排行榜
     * @param start 开始索引
     * @param end 结束索引
     * @return 总排行榜书籍列表
     */
    R<List<Book>> rankBook(Integer start, Integer end);


    /**
     * 时间连续排行榜
     * @param start 开始索引
     * @param end 结束索引
     * @return 总排行榜书籍列表
     */
    R<List<Book>> rankSmoothBook(Integer start, Integer end);

    /**
     * 定时任务，Redis信息持久化到MySQL数据库中
     */
    void updateDatabase();

}
