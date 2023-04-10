package com.card.game.oatuh.handler;

import com.card.game.common.result.Result;
import com.card.game.common.web.utils.ServletUtils;
import com.card.game.oatuh.threadlocal.ExceptionThreadLocal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 未认证处理器
 *
 * @author tomyou
 * @version 1.0 created on 2022/10/13 14:44
 */
@Component
@RequiredArgsConstructor
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ExceptionThreadLocal exceptionThreadLocal;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ServletUtils.writeToJson(response, Result.error(exceptionThreadLocal.getResultCode()));
        exceptionThreadLocal.clearContext();
    }
}
