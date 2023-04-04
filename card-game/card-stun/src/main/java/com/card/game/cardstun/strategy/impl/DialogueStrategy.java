package com.card.game.cardstun.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.card.game.cardstun.flyweight.ConnectionFactory;
import com.card.game.cardstun.model.ConnectionEntity;
import com.card.game.cardstun.model.Message;
import com.card.game.cardstun.strategy.OperateStrategy;
import lombok.val;

import java.io.IOException;

/**
 * 给你房间所有人发信息
 */
public class DialogueStrategy implements OperateStrategy {
    @Override
    public void operate(Message message, ConnectionEntity connection) {
        val room = ConnectionFactory.getRoom(message.getRoomId());
        val connections = room.queryAll();
        connections.forEach(con->{
            try {
                con.getSession().getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
