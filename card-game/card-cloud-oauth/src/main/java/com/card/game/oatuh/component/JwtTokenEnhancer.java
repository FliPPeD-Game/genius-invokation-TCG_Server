package com.card.game.oatuh.component;


import com.card.game.oatuh.domain.SecurityUser;
import com.card.game.oatuh.support.userdetails.SecurityMailUserDetails;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

/**
 * JWT内容增强器
 *
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        SecurityMailUserDetails securityUser = (SecurityMailUserDetails) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();
        //把用户ID设置到JWT中
        info.put("id", securityUser.getSysUserDTO().getId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
