package com.card.game.cardstun.flyweight;

import com.card.game.common.redis.RedisIdWorker;
import com.card.game.common.web.utils.SpringContextHolder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 享元工厂
 */


public class ConnectionFactory {

    /**
     * 所有房间
     */
    private static final Map<String, IFlyweight> rooms = new ConcurrentHashMap<>();

    public static IFlyweight createRoom(String roomId, Class<IFlyweight> clas)
            throws InstantiationException, IllegalAccessException {
        if (!rooms.containsKey(roomId)) {
            RedisIdWorker redisIdWorker = SpringContextHolder.getApplicationContext().getBean(RedisIdWorker.class);
            Integer createRoomId = redisIdWorker.nextId(roomId);
            rooms.put(String.valueOf(createRoomId), clas.newInstance());
        }
        return rooms.get(roomId);
    }

    /**
     * 获取房间
     *
     * @param roomId 房间ID
     * @return 房间
     */
    public static IFlyweight getRoom(String roomId) {
        return rooms.get(roomId);
    }
}
