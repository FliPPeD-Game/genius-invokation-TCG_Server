package com.card.game.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CardGameGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardGameGatewayApplication.class, args);
    }

}
