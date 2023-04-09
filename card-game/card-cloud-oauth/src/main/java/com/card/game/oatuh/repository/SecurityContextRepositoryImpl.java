package com.card.game.oatuh.repository;

import com.card.game.common.result.ResultCode;

import com.card.game.oatuh.constant.MessageConstant;
import com.card.game.oatuh.strategy.SecurityContextManager;
import com.card.game.oatuh.threadlocal.ExceptionThreadLocal;
import com.card.game.oatuh.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author tomyou
 * @version v1.0 2023-01-08-6:14 PM
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityContextRepositoryImpl extends SecurityContextRepositoryAdapter {
    private final ExceptionThreadLocal exceptionThreadLocal;

    private final SecurityContextManager securityContextManager;

    private final String websocketUrl = "/websocket";

    @Override
    public Supplier<SecurityContext> loadContext(HttpServletRequest request) {
        return () -> getSecurityContext(request);
    }

    @SneakyThrows
    public SecurityContext getSecurityContext(HttpServletRequest request) {
        log.info("processing authentication for [{}]", request.getRequestURI());
        //获取token
        String token;
        if (websocketUrl.matches(request.getRequestURI())) {
            token = request.getHeader("Sec-WebSocket-Protocol");
        } else {
            token = request.getHeader(MessageConstant.AUTHORIZATION);
        }


        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();

        if (Objects.isNull(token)) {
            return emptyContext;
        }

        Claims claims;
        try {
            token = token.replace(MessageConstant.AUTHORIZATION_PREFIX, MessageConstant.EMPTY_STR);
            claims = JwtUtil.parseJWT(token);
        } catch (SignatureException e) {
            log.error(e.getMessage());
            exceptionThreadLocal.setResultCode(ResultCode.ILLEGAL_TOKEN);
            return emptyContext;
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            exceptionThreadLocal.setResultCode(ResultCode.EXPIRED_TOKEN);
            return emptyContext;
        } catch (Exception e) {
            log.error(e.getMessage());
            exceptionThreadLocal.setResultCode(ResultCode.ERROR);
            return emptyContext;
        }

        return securityContextManager.handleContext(emptyContext, claims);
    }
}
