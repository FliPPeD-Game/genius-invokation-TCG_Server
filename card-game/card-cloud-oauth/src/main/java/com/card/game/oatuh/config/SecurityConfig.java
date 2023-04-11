package com.card.game.oatuh.config;

import com.card.game.oatuh.handler.SecurityAccessDeniedHandler;
import com.card.game.oatuh.handler.SecurityAuthenticationEntryPoint;
import com.card.game.oatuh.repository.SecurityContextRepositoryImpl;
import com.card.game.oatuh.support.email.MailAuthenticationConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author tomyou
 * @version v1.0 2023-01-07-12:07 PM
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableConfigurationProperties({SecurityUrlProperties.class})
@RequiredArgsConstructor
public class SecurityConfig  {

    private final MailAuthenticationConfigurer mailAuthenticationConfigurer;

    private final SecurityUrlProperties securityUrlProperties;

    private final SecurityContextRepositoryImpl securityContextRepository;

    private final SecurityAccessDeniedHandler securityAccessDeniedHandler;

    private final SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;

//    @Bean
//    public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        //关闭csrf
//        httpSecurity.csrf().disable();
//
//        //关闭表单登录
//        httpSecurity.formLogin().disable();
//
//        //禁用掉session
//        httpSecurity.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .enableSessionUrlRewriting(true);
//
//        //设置未认证处理器
//        httpSecurity.exceptionHandling()
//                .authenticationEntryPoint(securityAuthenticationEntryPoint)
//                .accessDeniedHandler(securityAccessDeniedHandler);
//
//
//        //拦截请求设置
//        httpSecurity.authorizeRequests()
//                .antMatchers(securityUrlProperties.getUrls().toArray(new String[0])).permitAll()
//                .anyRequest().authenticated();
//
//        //适配邮箱登陆
//        httpSecurity.apply(mailAuthenticationConfigurer);
//
//        // securityContext持久化配置
//        httpSecurity.securityContext((securityContext -> {
//            securityContext.requireExplicitSave(true);
//            securityContext.securityContextRepository(securityContextRepository);
//        }));
//
//        return httpSecurity.build();
//    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
