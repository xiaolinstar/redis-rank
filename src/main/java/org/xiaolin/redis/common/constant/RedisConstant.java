package org.xiaolin.redis.common.constant;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author xlxing
 * @Description 全局常量
 * @create 2023/5/30
 */
public class RedisConstant {
    /**
     * 总排行榜
     */
    public static String SORT_KEY = "book:sort";

    /**
     * 最近一天累计排行榜
     */
    public static String SORT_DAY_PREFIX = "book:day:";

    public static String SORT_HOUR_PREFIX = "book:hour:";

    public static String SMOOTH_SORT_KEY = "book:smooth";
    public static Double BETA = 1.1;

    public static String getSortKey() {
        return SORT_KEY;
    }
    public static String getSortDayKey() {
        return SORT_DAY_PREFIX + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
    public static String getSortHourKey() {
        return SORT_HOUR_PREFIX + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
    }

    public static String getSmoothSortKey() {
        // 获取当前日期，每天的02:01:00作为benchTime
        return SMOOTH_SORT_KEY + getSmoothSortDateTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
    public static String getLastSmoothSortKey() {
        return SMOOTH_SORT_KEY + getSmoothSortDateTime(1L).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    private static LocalDateTime getLocalDateTime(LocalDateTime now) {
        LocalTime time = LocalTime.of(2, 1, 0);
        if (now.toLocalTime().isAfter(time)) {
            return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 2, 1, 0);
        } else {
            LocalDateTime lastDayTime = now.minusDays(1L);
            return LocalDateTime.of(lastDayTime.getYear(), lastDayTime.getMonth(), lastDayTime.getDayOfMonth(), 2, 1, 0);
        }
    }

    public static LocalDateTime getSmoothSortDateTime(long minusDays) {
        LocalDateTime now = LocalDateTime.now().minusDays(minusDays);
        return getLocalDateTime(now);
    }

    public static LocalDateTime getSmoothSortDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return getLocalDateTime(now);
    }

    public static String getLastSortHourKey() {
        return SORT_HOUR_PREFIX + LocalDateTime.now().minusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
    }

    public static String getLastSortDayKey() {
        return SORT_DAY_PREFIX + LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
