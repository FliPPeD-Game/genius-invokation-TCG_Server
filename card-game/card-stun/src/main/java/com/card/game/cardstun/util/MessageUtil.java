package com.card.game.cardstun.util;

import com.alibaba.fastjson2.JSON;
import com.card.game.cardstun.model.Message;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;

@Slf4j
public class MessageUtil {
    public void sentMessage(Message message, Session session) {
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
