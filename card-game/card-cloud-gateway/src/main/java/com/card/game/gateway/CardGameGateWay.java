package com.card.game.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author tomyou
 * @version v1.0 2023/4/4 09:37
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class CardGameGateWay {
    public static void main(String[] args) {
        SpringApplication.run(CardGameGateWay.class, args);
    }
}
