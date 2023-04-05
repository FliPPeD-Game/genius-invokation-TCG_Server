package com.card.game.cardstun.model;

import javax.websocket.Session;
import lombok.Data;

/**
 *
 *
 * @author cunzhiwang
 * @Date 2023/4/4 12:45
 */
@Data
public class ConnectionEntity {
    private String ip;
    private Session session;
}
