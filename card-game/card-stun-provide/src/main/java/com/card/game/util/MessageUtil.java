package com.card.game.util;

import com.alibaba.fastjson2.JSON;
import com.card.game.model.MessageEntity;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;

@Slf4j
public class MessageUtil {
    public static void sentMessage(MessageEntity message, Session session) {
        try {
            session.getBasicRemote().sendText(JSON.toJSONString(message));
        } catch (IOException e) {
            log.info("发送失败");
        } finally {
            try {
                session.close();
            } catch (IOException e) {
                log.info("关闭失败");
            }
        }
    }
}
