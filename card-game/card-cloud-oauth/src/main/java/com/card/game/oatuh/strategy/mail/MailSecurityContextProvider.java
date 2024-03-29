package com.card.game.oatuh.strategy.mail;

import com.card.game.common.redis.RedisCache;
import com.card.game.common.redis.constants.RedisPrefixConstant;
import com.card.game.common.result.ResultCode;
import com.card.game.oatuh.constant.JwtConstants;
import com.card.game.oatuh.enums.SecurityLoginType;
import com.card.game.oatuh.strategy.SecurityContextProvider;
import com.card.game.oatuh.support.email.MailAuthenticationToken;
import com.card.game.oatuh.support.userdetails.SecurityMailUserDetails;
import com.card.game.oatuh.threadlocal.ExceptionThreadLocal;
import io.jsonwebtoken.Claims;
import java.util.Collections;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author tomyou
 * @version v1.0 2023-01-08-9:09 PM
 */
@Component
@RequiredArgsConstructor
public class MailSecurityContextProvider implements SecurityContextProvider {
    private final RedisCache redisCache;

    private final ExceptionThreadLocal exceptionThreadLocal;

    @Override
    public SecurityContext handleContext(SecurityContext securityContext, Claims claims) {
        String mailAccount = (String) claims.get(JwtConstants.SUBJECT);
        if (mailAccount != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String redisKey = RedisPrefixConstant.AUTHENTICATION_PREFIX + mailAccount;
            SecurityMailUserDetails userDetails = redisCache.getCacheObject(redisKey);
            //代表用户已经过期，需要重新进行登陆
            if (Objects.isNull(userDetails)) {
                exceptionThreadLocal.setResultCode(ResultCode.NEED_REQUIRE_LOGIN);
                return securityContext;
            }
            MailAuthenticationToken authenticated = MailAuthenticationToken
                    .authenticated(userDetails, Collections.emptyList());

            securityContext.setAuthentication(authenticated);
            SecurityContextHolder.setContext(securityContext);
            return securityContext;
        }
        return securityContext;
    }

    @Override
    public boolean supports(String typeCode) {
        return SecurityLoginType.MAIL.getValue().equals(typeCode);
    }
}
