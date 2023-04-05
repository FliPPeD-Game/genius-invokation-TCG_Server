package com.card.game;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @author tomyou
 * @version v1.0 2023-01-07-7:49 PM
 */
@SpringBootApplication
@MapperScan("com.card.game.mapper")
@ServletComponentScan(basePackages ="com.card.game.cardstun.*" )
public class CardGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardGameApplication.class, args);
    }
}
