package com.card.game.strategy.impl;

import com.card.game.flyweight.ConnectionFactory;
import com.card.game.flyweight.ConnectionFlyweight;
import com.card.game.flyweight.IFlyweight;
import com.card.game.model.ConnectionEntity;
import com.card.game.model.MessageEntity;
import com.card.game.strategy.OperateStrategy;
import com.card.game.common.redis.RedisIdWorker;
import com.card.game.common.redis.constants.RedisPreKey;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * 创建房间
 *
 * @author cunzhiwang
 * @Date 2023/4/4 17:05
 */
@Service
public class CreateRoomStrategy implements OperateStrategy {

    @Resource
    private  RedisIdWorker redisIdWorker;

    @Override
    public void operate(MessageEntity message, ConnectionEntity connection) {
        if (StringUtils.isBlank(message.getPeerId())) {
            message.setMessage("peerID不能为空");
            message.setCode(HttpStatus.BAD_REQUEST.value());
            return;
        }
        Integer roomId = redisIdWorker.nextId(RedisPreKey.ROOM_ID);
        IFlyweight room = ConnectionFactory.createRoom(roomId, ConnectionFlyweight.class);
        room.join(connection);
        message.setRoomId(String.valueOf(roomId));
        message.setMessage("房间创建成功");
        message.setCode(HttpStatus.OK.value());
    }
}
