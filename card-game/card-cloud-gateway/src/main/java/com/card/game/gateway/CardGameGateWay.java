package com.card.game.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author tomyou
 * @version v1.0 2023/4/4 09:37
 **/
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.card.game.common.redis"})
public class CardGameGateWay {
    public static void main(String[] args) {
        SpringApplication.run(CardGameGateWay.class, args);
    }
}
