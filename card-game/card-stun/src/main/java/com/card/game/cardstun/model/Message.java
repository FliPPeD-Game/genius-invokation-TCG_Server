package com.card.game.cardstun.model;

import lombok.Data;

/**
 * @ClassName: Message
 * @Description: TODO
 * @auther: cunzhiwang
 */
@Data
public class Message {
    private String command;
    private String userId;
    private String roomId;
    private String message;
    private String peerID;
    private Integer code;

}
