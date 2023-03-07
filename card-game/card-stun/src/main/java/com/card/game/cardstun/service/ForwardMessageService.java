package com.card.game.cardstun.service;

import com.alibaba.fastjson2.JSON;
import com.card.game.cardstun.model.Message;
import com.card.game.cardstun.websocket.Connection;
import java.util.Collection;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * @ClassName: MessageService
 * @Description: 处理websocket消息
 * @auther: cunzhiwang
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ForwardMessageService {

    private final RoomService roomService;

    /**
     * 给房间内的所有人发送消息（包括自己）
     */

    public void sendMessageForEveryInRoom(Message message) {
        Set<Connection> room = roomService.queryRoomById(message.getRoomId());
        for (Connection connection : room) {
            try {
                connection.getSession().getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (IOException e) {
                log.error(message.getUserId() + "发送消息失败");
                e.printStackTrace();
            }
        }
    }



    /**
     * 给房间除自己之外的所有人发送消息
     */
    public void sendMessageForEveryExcludeSelfInRoom(Message message) {
        Set<Connection> room = roomService.queryRoomById(message.getRoomId());
        for (Connection connection : room) {
            try {
                if(!connection.getUserId().equals(message.getUserId())) {
                    connection.getSession().getBasicRemote().sendText(JSON.toJSONString(message));
                }
            } catch (IOException e) {
                log.error(message.getUserId()+"向房间："+message.getRoomId() + "发送消息失败");
                e.printStackTrace();
            }
        }
    }


    /**
     * 给在线的所有人发送消息（包括自己）
     */
    public void sendMessageForAllOnline(Message message) {
        Collection<Set<Connection>> rooms = roomService.queryAllRoom();
        for (Set<Connection> room : rooms) {
            for (Connection connection : room) {
                try {
                    connection.getSession().getBasicRemote().sendText(JSON.toJSONString(message));
                } catch (IOException e) {
                    log.error(message.getUserId()+"发送广播失败");
                    e.printStackTrace();
                }
            }

        }
    }

}
