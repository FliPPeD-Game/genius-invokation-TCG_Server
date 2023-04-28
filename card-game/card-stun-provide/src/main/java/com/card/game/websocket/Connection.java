package com.card.game.websocket;


import com.alibaba.fastjson2.JSON;
import com.card.game.config.ConfiguratorForClientIp;
import com.card.game.constants.CommandType;
import com.card.game.model.ConnectionEntity;
import com.card.game.model.MessageEntity;
import com.card.game.service.CommandService;
import com.card.game.service.ForwardMessageService;
import com.card.game.service.RoomService;
import com.card.game.strategy.Context;
import com.card.game.util.MessageUtil;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @ClassName: WebSocketServer
 * @Description: @ServerEndpoint不是单例模式
 * @auther: cunzhiwang
 */
@ServerEndpoint(value = "/websocket", configurator = ConfiguratorForClientIp.class)
@Component
@Slf4j
//@Data 由于@Data重写了hashCode()和equals()方法，会导致Set<Connection> remove元素时，找不到正确的元素，应用@Setter @Getter @ToString替换
@Getter
@Setter
@ToString
public class Connection {

    //在线总人数
    private static volatile AtomicInteger onlineCount = new AtomicInteger(0);

    private static RoomService roomService;

    @Autowired
    public void setRoomService(RoomService roomService) {
        Connection.roomService = roomService;
    }


    private static ForwardMessageService forwardMessageService;

    @Autowired
    public void setDialogueService(ForwardMessageService forwardMessageService) {
        Connection.forwardMessageService = forwardMessageService;
    }


    private static CommandService commandService;

    @Autowired
    public void setCommandService(CommandService commandService) {
        Connection.commandService = commandService;
    }

    //某个客户端的ip
    private String ip;

    //某个客户端的userID
    private String userId;

    //某个客户端的roomNo
    private String roomId;

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        ip = (String) session.getUserProperties().get("clientIp");
        //未进任何房间时，将本次连接放到大厅里面
        roomService.enterLobby(this);
        log.info("用户:" + ip + "连接到服务器,当前在线人数为" + onlineCount.incrementAndGet());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        //离开大厅
        roomService.leaveLobby(this);
        //即使连接错误，回调了onError方法，最终也会回调onClose方法，所有退出房间写在这里比较合适
        roomService.leaveRoom(roomId, this);
        //在线数减1
        log.info("用户:" + ip + "关闭连接，退出房间" + roomId + "当前在线人数为" + onlineCount.addAndGet(-1));
    }

    /**
     * 连接发生错误时调用的方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户:" + ip + "连接错误");
        error.printStackTrace();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param stringMessage 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(Session session, String stringMessage) throws InstantiationException, IllegalAccessException {
        MessageEntity message = JSON.parseObject(stringMessage, MessageEntity.class);
        ConnectionEntity connectionEntity = new ConnectionEntity();
        connectionEntity.setIp(ip);
        connectionEntity.setSession(session);
        if (!CommandType.isExist(message.getCommand())) {
            message.setRoomId(null);
            message.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            message.setMessage("命令输入错误");
            MessageUtil.sentMessage(message, session);
            return;
        }
        CommandType command = CommandType.getCommand(message.getCommand());
        Context context = new Context(command.getType().newInstance());
        context.operate(message, connectionEntity);
        MessageUtil.sentMessage(message, session);
    }


}
