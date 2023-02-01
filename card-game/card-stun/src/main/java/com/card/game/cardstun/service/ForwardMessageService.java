package com.example.geniusinvokationtcg_stun.service;

import com.alibaba.fastjson.JSON;
import com.example.geniusinvokationtcg_stun.model.Message;
import com.example.geniusinvokationtcg_stun.websocket.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * @ClassName: MessageService
 * @Description: 处理websocket消息
 * @auther: caiwei
 * @date: 2019/8/17 18:31
 */
@Slf4j
@Service
public class ForwardMessageService {

    @Autowired
    private RoomService roomService;

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
