package com.card.game.websocket.server;

import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author tomyou
 * @version 1.0 created on 2023/1/16 17:44
 */
@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")
public class WebSocketServer {




}
