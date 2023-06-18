package org.xiaolin.redis.exception;

/**
 * @author xlxing
 * @Description
 * @create 2023/5/31
 */
public class GlobalException extends RuntimeException {
    public GlobalException(String message) {
        super(message);
    }
}
