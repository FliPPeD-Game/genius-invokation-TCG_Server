package com.card.game.security.strategy;

import com.card.game.security.constant.JwtConstants;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tomyou
 * @version v1.0 2023-01-08-8:54 PM
 */
@Component
public class SecurityContextProviderManager implements SecurityContextManager {

    private final List<SecurityContextProvider> providers;

    public SecurityContextProviderManager(List<SecurityContextProvider> providers) {
        this.providers = providers;
    }

    @Override
    public SecurityContext handleContext(SecurityContext securityContext, Claims claims) {
        SecurityContext result = null;
        String typeCode = (String) claims.get(JwtConstants.TYPE_CODE);
        for (SecurityContextProvider provider : providers) {
            if (!provider.supports(typeCode)) {
                continue;
            }
            result = provider.handleContext(securityContext, claims);
        }
        return result;
    }
}
