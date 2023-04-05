package com.card.game.cardstun.strategy.impl;

import com.card.game.cardstun.flyweight.ConnectionFactory;
import com.card.game.cardstun.flyweight.ConnectionFlyweight;
import com.card.game.cardstun.flyweight.IFlyweight;
import com.card.game.cardstun.model.ConnectionEntity;
import com.card.game.cardstun.model.MessageEntity;
import com.card.game.cardstun.strategy.OperateStrategy;
import com.card.game.common.redis.RedisIdWorker;
import com.card.game.common.redis.constants.RedisPreKey;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CreateRoomStrategy implements OperateStrategy {

    private final RedisIdWorker redisIdWorker;

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
