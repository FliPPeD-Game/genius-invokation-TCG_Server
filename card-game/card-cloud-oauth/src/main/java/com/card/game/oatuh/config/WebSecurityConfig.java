package com.card.game.oatuh.config;

import com.card.game.oatuh.handler.SecurityAccessDeniedHandler;
import com.card.game.oatuh.handler.SecurityAuthenticationEntryPoint;
import com.card.game.oatuh.repository.SecurityContextRepositoryImpl;
import com.card.game.oatuh.support.email.MailAuthenticationConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * SpringSecurity配置
 *
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties({SecurityUrlProperties.class})
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final MailAuthenticationConfigurer mailAuthenticationConfigurer;

    private final SecurityUrlProperties securityUrlProperties;

    private final SecurityContextRepositoryImpl securityContextRepository;

    private final SecurityAccessDeniedHandler securityAccessDeniedHandler;

    private final SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;
    //
    @Override
    protected void configure(HttpSecurity  httpSecurity) throws Exception {
        //关闭csrf
        httpSecurity.csrf().disable();

        //关闭表单登录
        httpSecurity.formLogin().disable();

        //禁用掉session
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .enableSessionUrlRewriting(true);

        //设置未认证处理器
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(securityAuthenticationEntryPoint)
                .accessDeniedHandler(securityAccessDeniedHandler);
        //拦截请求设置
        httpSecurity.authorizeRequests()
                .antMatchers(securityUrlProperties.getUrls().toArray(new String[0])).permitAll()
                .anyRequest().authenticated();

        //适配邮箱登陆
        httpSecurity.apply(mailAuthenticationConfigurer);

        // securityContext持久化配置
        httpSecurity.securityContext((securityContext -> {
            securityContext.requireExplicitSave(true);
            securityContext.securityContextRepository(securityContextRepository);
        }));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
