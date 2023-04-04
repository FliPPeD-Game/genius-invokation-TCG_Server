package com.card.game.cardstun.strategy.impl;

import com.card.game.cardstun.flyweight.ConnectionFactory;
import com.card.game.cardstun.flyweight.IFlyweight;
import com.card.game.cardstun.model.ConnectionEntity;
import com.card.game.cardstun.model.Message;
import com.card.game.cardstun.strategy.OperateStrategy;
import org.springframework.http.HttpStatus;

public class EnterRoomStrategy implements OperateStrategy {

    @Override
    public void operate(Message message, ConnectionEntity connection) {
        IFlyweight room = ConnectionFactory.getRoom(message.getRoomId());
        if (room == null) {
            message.setMessage("房间不存在");
            message.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return;
        }
        if (room.query() > 2) {
            message.setMessage("房间人数已满，请另寻他路");
            message.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else {
            room.join(connection);
            message.setMessage("房间加入成功");
            message.setCode(HttpStatus.OK.value());
        }
    }
}
