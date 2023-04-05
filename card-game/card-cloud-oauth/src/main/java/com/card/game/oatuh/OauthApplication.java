package com.card.game.oatuh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author tomyou
 * @version v1.0 2023/4/5 10:36
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class OauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }
}
