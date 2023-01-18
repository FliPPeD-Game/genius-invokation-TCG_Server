package com.card.game.message;

import com.card.game.message.pojo.Message;

/**
 * @author tomyou
 * @version v1.0 2023-01-15-9:35 PM
 */
public interface MessageManager {

    /**
     * 发送消息
     *
     * @param message 消息
     * @return 是否发送成功
     */
    Boolean sendMessage(Message message);
}
