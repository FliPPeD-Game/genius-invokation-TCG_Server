package com.card.game.common.redis;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis全局分布式id生成
 *
 * @author cunzhiwang
 * @Date 2023/2/2 14:17
 */
@Component
@RequiredArgsConstructor
public class RedisIdWorker {
    private static final long BEGIN_TIMESTAMP = 1640995200L;

    private static final int COUNT_BITS = 32;

    private final StringRedisTemplate stringRedisTemplate;

    public Long nextId(String keyPrefix) {
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        //获取当前时间得秒数
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long time = nowSecond - BEGIN_TIMESTAMP;

        String format = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // Redis Incrby 命令将 key 中储存的数字加上指定的增量值。
        // 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 Incrby 命令。
        Long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + format);

        return time << COUNT_BITS | count;
    }
     public static void main(String[] args) {
            LocalDateTime of = LocalDateTime.of(2022, 1, 1, 0, 0, 0);
            long l = of.toEpochSecond(ZoneOffset.UTC);
            // LocalTime类的toEpochSecond()方法用于
            // 将此LocalTime转换为自1970-01-01T00：00：00Z以来的秒数
            System.out.println(l);
     }
}
