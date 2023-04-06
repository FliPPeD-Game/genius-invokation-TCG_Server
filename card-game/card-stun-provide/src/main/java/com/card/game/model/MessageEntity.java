package com.card.game.model;

import lombok.Data;

/**
 * @ClassName: Message
 * @Description: TODO
 * @auther: cunzhiwang
 */
@Data
public class MessageEntity {
    private String command;
    private String userId;
    private String roomId;
    private String message;
    private String peerId;
    private Integer code;

}
