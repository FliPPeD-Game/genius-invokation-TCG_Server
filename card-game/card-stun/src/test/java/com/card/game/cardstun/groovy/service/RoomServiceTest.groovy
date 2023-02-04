package com.card.game.cardstun.groovy.service

import com.card.game.cardstun.service.RoomService
import com.card.game.cardstun.websocket.Connection
import com.card.game.common.redis.RedisIdWorker
import com.card.game.common.redis.constant.RedisPreKey
import org.junit.Assert
import org.mockito.Mock
import org.springframework.boot.test.mock.mockito.MockBean
import spock.lang.Specification

import static org.mockito.Mockito.when


/**
 * 房间服务类测试
 *
 * @author cunzhiwang
 * @Date 2023/2/2 11:58
 */
class RoomServiceTest extends Specification {
    @MockBean
    private RedisIdWorker redisIdWorker;
    def "test createRome"() {
        given:
        Connection connection=new Connection();
        RoomService roomService= new RoomService(redisIdWorker)
        when(redisIdWorker.nextId(RedisPreKey.ROOM_ID)).thenReturn(123456L);
        expect:

        Assert.assertEquals(123456L,roomService.createRoom(connection))

    }
}