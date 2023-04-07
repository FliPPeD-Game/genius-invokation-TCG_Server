package com.card.game.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.card.game.flyweight.ConnectionFactory;
import com.card.game.model.ConnectionEntity;
import com.card.game.model.MessageEntity;
import com.card.game.strategy.OperateStrategy;
import lombok.val;

import java.io.IOException;

/**
 * 给你房间所有人发信息
 */
public class DialogueStrategy implements OperateStrategy {
    @Override
    public void operate(MessageEntity message, ConnectionEntity connection) {
        val room = ConnectionFactory.getRoom(Integer.valueOf(message.getRoomId()));
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
