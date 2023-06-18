package org.xiaolin.redis.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 书籍
 * @author xlxing
 * @TableName book
 */
@TableName(value ="book")
@Data
@NoArgsConstructor
@Schema(description = "书籍")
public class Book {

    public Book(Long authorId, String name) {
        this.authorId = authorId;
        this.name = name;
        this.clickNum = 0;
        this.likeNum = 0;
        this.commentNum = 0;
    }
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "主键，系统自动生成")
    private Long id;

    /**
     * 书名
     */
    @Schema(description = "姓名", defaultValue = "Tom")
    private String name;

    /**
     * 书的作者id
     */
    @Schema(description = "作者id", defaultValue = "1L")
    private Long authorId;

    /**
     * 点击量
     */
    @Schema(description = "点击量", defaultValue = "0")
    private Integer clickNum;

    /**
     * 点赞数
     */
    @Schema(description = "点赞数", defaultValue = "0")
    private Integer likeNum;

    /**
     * 评论数
     */
    @Schema(description = "评论数", defaultValue = "0")
    private Integer commentNum;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}