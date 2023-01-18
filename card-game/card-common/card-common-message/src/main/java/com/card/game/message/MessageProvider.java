package com.card.game.message;

import com.card.game.message.pojo.Message;

/**
 * @author tomyou
 * @version v1.0 2023-01-15-9:37 PM
 */
public interface MessageProvider {

    /**
     * 发送消息
     *
     * @param message 消息
     * @return 是否发送成功
     */
    Boolean sendMessage(Message message);


    /**
     * 是否支持该provider
     *
     * @param message 消息class
     * @return 是否支持
     */
    boolean supports(Class<?> message);
}
