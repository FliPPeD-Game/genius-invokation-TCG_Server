package com.card.game.oatuh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
    @Configuration
    public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

        //作用: 用来将自定义AuthenticationManager在工厂中进行暴露,可以在任何位置注入
        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

}
