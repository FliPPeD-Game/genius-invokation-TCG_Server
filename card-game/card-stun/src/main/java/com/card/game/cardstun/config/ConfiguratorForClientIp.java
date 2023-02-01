package com.example.geniusinvokationtcg_stun.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @ClassName: ClientIpConfiguration
 * @Description: 在modifyHandshake方法中，可以将储存在httpSession中的clientIp，转移到ServerEndpointConfig中
 * @auther: cunzhiwang
 * @date: 2019/8/18 00:40
 */
public class ConfiguratorForClientIp extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        //把HttpSession中保存的ClientIP放到ServerEndpointConfig中
        config.getUserProperties().put("clientIp", httpSession.getAttribute("clientIp"));
    }
}
