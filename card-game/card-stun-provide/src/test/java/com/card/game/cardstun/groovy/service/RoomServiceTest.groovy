package com.card.game.cardstun.groovy.service

import com.card.game.cardstun.service.RoomService
import com.card.game.cardstun.websocket.Connection
import com.card.game.common.redis.RedisIdWorker
import com.card.game.common.redis.constants.RedisPreKey
import org.junit.Assert
import org.springframework.data.redis.core.StringRedisTemplate
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when


/**
 * 房间服务类测试
 *
 * @author cunzhiwang
 * @Date 2023/2/2 11:58
 */
class RoomServiceTest extends Specification {


    private StringRedisTemplate redisTemplate=Mock(StringRedisTemplate.class);
    private RedisIdWorker redisIdWorker = new RedisIdWorker(redisTemplate);
    /**
     * 测试返回全局唯一自增id
     */
    def "test createRome"() {
        given:
        Connection connection=new Connection();
        when(redisTemplate.opsForValue().increment(any())).thenReturn(123456L)
        when(redisIdWorker.nextId(RedisPreKey.ROOM_ID)).thenReturn(123456L);
        RoomService roomService= new RoomService(redisIdWorker)
        expect:

        Assert.assertEquals(123456L,roomService.createRoom(connection))

    }
}