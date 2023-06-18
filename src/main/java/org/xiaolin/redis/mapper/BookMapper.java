package org.xiaolin.redis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.xiaolin.redis.domain.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author xlxing
* @description 针对表【book(书籍)】的数据库操作Mapper
* @createDate 2023-05-31 14:36:39
* @Entity org.xiaolin.redis.domain.Book
*/
@Mapper
public interface BookMapper extends BaseMapper<Book> {

}




