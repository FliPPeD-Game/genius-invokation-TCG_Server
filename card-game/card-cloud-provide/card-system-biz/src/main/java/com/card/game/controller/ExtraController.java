package com.card.game.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: QueryWebSocketController
 * @Description: TODO
 * @auther: cunzhiwang
 * @date: 2019/8/15 14:26
 */
@RestController
@RequiredArgsConstructor
public class ExtraController {

//    private final RoomService roomService;


    //拿到本机在wifi中的局域网ip
    static {
        // 获得本机的所有网络接口
        Enumeration<NetworkInterface> nifs;
        try {
            nifs = NetworkInterface.getNetworkInterfaces();
            while (nifs.hasMoreElements()) {
                NetworkInterface nif = nifs.nextElement();

                // 获得与该网络接口绑定的 IP 地址，一般只有一个
                Enumeration<InetAddress> addresses = nif.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // 获取IPv4 地址
                    if (addr instanceof Inet4Address) {
                        if ("wlan0".equals(nif.getName())) {
                            String ipOfInet4Address = addr.getHostAddress();
                        }
                        System.out.println("网卡接口名称：" + nif.getName());
                        System.out.println("网卡接口地址：" + addr.getHostAddress());
                        System.out.println();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }


    }

    @Value("${server.port:8843}")
    private Integer port;

    @GetMapping("/getWebSocketUrl")
    public Map<String, String> getIpAddress() {
        Map<String, String> result = new HashMap<>();
        //http用ws,https用wss,ws可以用ip访问，wss的话，用ip会报证书错误，需要用域名访问,
        //TODO 局域网在C:\Windows\System32\drivers\etc下的hosts修改ip映射域名
        //result.put("url", "wss:" + ipOfInet4Address +":"+port+ "/websocket");
        result.put("url", "ws:127.0.0.1:"+port+ "/websocket");
        return result;
    }

    @GetMapping("/queryCountInRoom")
    public Map<String, String> queryCountInRoom(String roomId) {
        Map<String, String> result = new HashMap<>();
        //result.put("count", String.valueOf(roomService.queryCountInRoom(roomId)));
        return result;
    }
}
