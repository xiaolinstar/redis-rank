package org.xiaolin.redis.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xiaolin.redis.common.resp.R;

/**
 * @author xlxing
 * @Description
 * @create 2023/5/31
 */

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(GlobalException.class)
    public R<Void> handleGlobalException(GlobalException e) {
        return R.error(e.getMessage());
    }
}
