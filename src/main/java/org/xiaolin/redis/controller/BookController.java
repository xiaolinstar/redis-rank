package org.xiaolin.redis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaolin.redis.common.req.BookAddDto;
import org.xiaolin.redis.common.req.BookUpdateDto;
import org.xiaolin.redis.common.resp.R;
import org.xiaolin.redis.domain.Book;
import org.xiaolin.redis.service.BookService;

import java.util.List;

/**
 * @author xlxing
 * @Description 书籍控制器
 * @create 2023/5/31
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Tag(name = "书籍管理", description = "书籍信息的增删改查以及查阅排行榜")
public class BookController {
    private final BookService bookService;

    /**
     * 根据书籍id查看书籍信息
     * @param bookId 书籍主键
     * @return 书
     */
    @Operation(summary = "查阅书籍信息")
    @GetMapping("/{id}")
    public R<Book> one(@PathVariable("id") Long bookId) {
        return bookService.getBook(bookId);
    }

    @Operation(summary = "分页查询书籍")
    @GetMapping("/page")
    public R<Page<Book>> pageBooks(@RequestParam int page, @RequestParam int pageSize) {
        return bookService.getBookPage(page, pageSize);
    }

    @Operation(summary = "上架新书")
    @PostMapping
    public R<Void> addOne(@RequestBody BookAddDto bookAddDto) {
        return bookService.addBook(bookAddDto);
    }

    @Operation(summary = "更新书籍")
    @PutMapping()
    public R<Void> updateOne(@RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.updateBook(bookUpdateDto);
    }

    @Operation(summary = "查询当天排行榜")
    @GetMapping("/rank/day")
    public R<List<Book>> rankDayRange(@RequestParam Integer start, @RequestParam Integer end) {
        return bookService.rankDayBook(start, end);
    }

    @Operation(summary = "查询时排行榜")
    @GetMapping("/rank/hour")
    public R<List<Book>> rankHourRange(@RequestParam Integer start, @RequestParam Integer end) {
        return bookService.rankHourBook(start, end);
    }

    @Operation(summary = "查询渐进式排行榜")
    @GetMapping("/rank/smooth")
    public R<List<Book>> rankSmoothRange(@RequestParam Integer start, @RequestParam Integer end) {
        return bookService.rankSmoothBook(start, end);
    }

    @Operation(summary = "查询总排行榜")
    @GetMapping("/rank")
    public R<List<Book>> rankRange(@RequestParam Integer start, @RequestParam Integer end) {
        return bookService.rankBook(start, end);
    }

    @Operation(summary = "删除书籍")
    @DeleteMapping("/{id}")
    public R<Void> deleteOne(@PathVariable("id") Long bookId) {
        return bookService.deleteBook(bookId);
    }
}
