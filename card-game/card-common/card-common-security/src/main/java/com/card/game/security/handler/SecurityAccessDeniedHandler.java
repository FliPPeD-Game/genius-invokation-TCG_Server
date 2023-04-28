package com.card.game.security.handler;


import com.card.game.common.result.Result;
import com.card.game.common.result.ResultCode;
import com.card.game.common.web.utils.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author tomyou
 * @version 1.0 created on 2022/10/19 9:32
 */
@Component
@Slf4j
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ServletUtils.writeToJson(response, Result.error(ResultCode.FORBIDDEN));
    }
}
