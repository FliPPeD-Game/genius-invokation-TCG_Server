package com.card.game.common.redis;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;

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
        Long id =time << COUNT_BITS | count;
        String idStr=String.valueOf(id);
        Random random=new Random();
        int i=0;
        while (true){
           i = random.nextInt(idStr.length());
            if(i+6<=idStr.length()){
                break;
            }
        }
        idStr=idStr.substring(i,i+6);
        return Long.valueOf(idStr);
    }
     public static void main(String[] args) {
         System.out.println(2<<2);
     }
}
