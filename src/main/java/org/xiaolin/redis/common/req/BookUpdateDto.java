package org.xiaolin.redis.common.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xlxing
 * @Description 书籍更新数据传输对象
 * @create 2023/6/18
 */

@Schema(description = "书籍更新数据传输对象")
@Getter
@AllArgsConstructor
public class BookUpdateDto {

    @Schema(description = "主键 书籍id")
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
}
