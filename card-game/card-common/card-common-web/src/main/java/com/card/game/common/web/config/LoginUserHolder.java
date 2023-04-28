package com.card.game.common.web.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.card.game.common.base.dto.UserInfoDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取用户登录信息
 *
 * @author cunzhiwang
 * @Date 2023/4/13 11:58
 */
@Component
public class LoginUserHolder {

    final String USER_FIELD = "user";
    final String USER_NAME_FIELD = "user_name";
    final String ID_FIELD = "id";
    final String AUTHORITIES_FIELD="authorities";

    public UserInfoDTO getCurrentUser() {
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String userStr = request.getHeader(USER_FIELD);
        JSONObject userJsonObject = new JSONObject(userStr);
        UserInfoDTO userDTO = new UserInfoDTO();
        userDTO.setUsername(userJsonObject.getStr(USER_NAME_FIELD));
        userDTO.setId(Convert.toLong(userJsonObject.get(ID_FIELD)));
        userDTO.setRoles(Convert.toList(String.class, userJsonObject.get(AUTHORITIES_FIELD)));
        return userDTO;
    }
}
