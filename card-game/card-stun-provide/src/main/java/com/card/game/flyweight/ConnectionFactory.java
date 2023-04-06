package com.card.game.flyweight;

import com.card.game.common.exception.BizException;
import com.card.game.common.redis.RedisIdWorker;
import com.card.game.common.redis.constants.RedisPreKey;
import com.card.game.common.web.utils.SpringContextHolder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.BeansException;

/**
 * 享元工厂
 */


public class ConnectionFactory {

    /**
     * 所有房间
     */
    private static final Map<Integer, IFlyweight> rooms = new ConcurrentHashMap<>();

    public static IFlyweight createRoom(Integer roomId, Class<? extends IFlyweight> clas) {
        if (!rooms.containsKey(roomId)) {
            try {
                RedisIdWorker redisIdWorker = SpringContextHolder.getApplicationContext().getBean(RedisIdWorker.class);
                Integer createRoomId = redisIdWorker.nextId(RedisPreKey.ROOM_ID);
                rooms.put(createRoomId, clas.newInstance());
            } catch (BeansException | InstantiationException | IllegalAccessException e) {
                throw new BizException("创建失败");
            }
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
