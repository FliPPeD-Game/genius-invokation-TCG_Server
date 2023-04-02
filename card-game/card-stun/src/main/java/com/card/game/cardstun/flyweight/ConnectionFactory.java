package com.card.game.cardstun.flyweight;

import com.card.game.common.redis.RedisIdWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 享元工厂
 */
@Component
@RequiredArgsConstructor
public class ConnectionFactory<T> {
    // 所有房间
    private Map<String, T> rooms = new ConcurrentHashMap<>();
    private final RedisIdWorker redisIdWorker;

    public T createRoom(String roomId, Class<T> clas) throws InstantiationException, IllegalAccessException {
        if (!rooms.containsKey(roomId)) {
            Integer createRoomId = redisIdWorker.nextId(roomId);
            rooms.put(String.valueOf(createRoomId), clas.newInstance());
        }
        return rooms.get(roomId);
    }
}
