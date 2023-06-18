package org.xiaolin.redis.common.resp;

import lombok.Getter;
import lombok.ToString;

/**
 * TODO 这个可以自动生成吧
 * @author xlxing
 * @Description
 * @create 2023/5/31
 */
@Getter
@ToString
public class R<T> {

    private final String message;
    private final String code;
    private final T data;

    public R(String message, String code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public R(T data) {
        this.message = "请求成功";
        this.code = "00000";
        this.data = data;
    }

    public R() {
        this.message = "请求成功";
        this.code = "00000";
        this.data = null;
    }

    public static <T> R<T> ok(T data) {
        return new R<>(data);
    }
    public static <T> R<T> ok() {
        return new R<>();
    }

    public static <T> R<T> error(String message) {
        return new R<>(message, "40001", null);
    }
}
