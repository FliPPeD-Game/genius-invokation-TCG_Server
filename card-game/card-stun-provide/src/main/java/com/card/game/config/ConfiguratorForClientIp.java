package com.card.game.config;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import java.util.List;

/**
 * @ClassName: ClientIpConfiguration
 * @Description: 在modifyHandshake方法中，可以将储存在httpSession中的clientIp，转移到ServerEndpointConfig中
 * @auther: cunzhiwang
 */
public class ConfiguratorForClientIp extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        List<String> headers = request.getHeaders().get("Sec-WebSocket-Protocol");
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        response.getHeaders().put("Sec-WebSocket-Protocol", headers);
        //把HttpSession中保存的ClientIP放到ServerEndpointConfig中
        config.getUserProperties().put("clientIp", httpSession.getAttribute("clientIp"));
    }
}
