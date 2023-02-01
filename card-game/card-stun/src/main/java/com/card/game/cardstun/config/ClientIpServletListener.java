package com.example.geniusinvokationtcg_stun.config;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: ClientIpServletListener
 * @Description: 根据JFR-356，Tomcat提供的WebSocket API并没有获取客户端IP地址的方法。
 *  所以我们无法直接在WebSocket类里面获取客户端IP地址等信息。
 *  但是通过监听ServletRequest并使用HttpSession和ServerEndpointConfig里面的Attributes传递信息，
 *  就可以实现直接在WebSocket类中获得客户端IP地址，弥补了WebSocket的一些先天不足。
 * @auther: cunzhiwang
 * @date: 2019/8/18 00:37
 */
@WebListener
public class ClientIpServletListener implements ServletRequestListener {
    @Override

    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }
    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request=(HttpServletRequest) servletRequestEvent.getServletRequest();
        HttpSession session=request.getSession();
        //把HttpServletRequest中的IP地址放入HttpSession中，关键字可任取，此处为clientIp
        session.setAttribute("clientIp", servletRequestEvent.getServletRequest().getRemoteAddr());
    }

}
