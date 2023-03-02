package com.card.game.security.utils;

import com.card.game.api.user.dto.AvatarDTO;
import com.card.game.api.user.dto.SysUserDTO;
import com.card.game.api.user.vo.SysUserVO;
import com.card.game.common.web.utils.BeanMapperUtils;
import com.card.game.security.constant.SecurityConstants;
import com.card.game.security.support.email.MailAuthenticationToken;
import com.card.game.security.support.userdetails.SecurityMailUserDetails;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

/**
 * @author tomyou
 * @version v1.0 2023/2/26 16:41
 **/
@Slf4j
public class SecurityContextUtils {

    public static Map<String, Object> buildUserMailContext(SecurityMailUserDetails principal, String token) {
        Map<String, Object> userMap = Maps.newHashMap();
        //存放信息返回给前端
        userMap.put(SecurityConstants.AUTHORIZATION, token);
        //密码擦除
        principal.clearPassword();
        SysUserDTO sysUserDTO = principal.getSysUserDTO();
        sysUserDTO.setAvatarInfo(
                AvatarDTO.builder()
                        .url(sysUserDTO.getAvatar())
                        .country(sysUserDTO.getCountry())
                        .build());
        SysUserVO sysUserVO = BeanMapperUtils.map(sysUserDTO, SysUserVO.class);
        userMap.put(SecurityConstants.USER_INFO, sysUserVO);
        return userMap;
    }

    public static SecurityMailUserDetails getCurrentUserInfo() {
        SecurityMailUserDetails securityMailUserDetails = null;
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            MailAuthenticationToken authentication = (MailAuthenticationToken) context.getAuthentication();
            securityMailUserDetails = (SecurityMailUserDetails) authentication.getPrincipal();
        } catch (Exception e) {
            log.error("getCurrentUserInfo has error:", e);
        }
        return securityMailUserDetails;
    }

}
