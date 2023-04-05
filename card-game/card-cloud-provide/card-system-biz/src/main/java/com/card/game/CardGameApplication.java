package com.card.game;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author tomyou
 * @version v1.0 2023-01-07-7:49 PM
 */
@SpringBootApplication
@MapperScan("com.card.game.mapper")
@ServletComponentScan(basePackages ="com.card.game.cardstun.*" )
@EnableDiscoveryClient
public class CardGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardGameApplication.class, args);
    }
}
