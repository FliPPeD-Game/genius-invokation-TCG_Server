package com.card.game.message.pojo;

import com.card.game.message.enums.TemplateEnum;

import java.io.Serializable;

/**
 * @author tomyou
 * @version v1.0 2023-01-15-9:26 PM
 */

public interface Message extends Serializable {

    /**
     * 获取接收者
     *
     * @return string
     */
    String getTo();

    /**
     * 获取发送者
     *
     * @return string
     */
    String getFrom();

    /**
     * 获取发送内容
     *
     * @return string
     */
    String getContent();


    TemplateEnum getTemplateEnum();
}
