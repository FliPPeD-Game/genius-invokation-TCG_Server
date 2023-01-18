package com.card.game.message.pojo;

import com.card.game.message.enums.TemplateEnum;
import lombok.Setter;

/**
 * @author tomyou
 * @version v1.0 2023-01-15-9:27 PM
 */
@Setter
public class AbstractMessage implements Message {
    /**
     * 发送者
     */
    private String from;


    /**
     * 接收者
     */
    private String to;


    /**
     * 发送消息的内容
     */
    private String content;

    /**
     * 模版枚举
     */
    private TemplateEnum templateEnum;

    @Override
    public String getTo() {
        return this.to;
    }

    @Override
    public String getFrom() {
        return this.from;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public TemplateEnum getTemplateEnum() {
        return this.templateEnum;
    }
}
