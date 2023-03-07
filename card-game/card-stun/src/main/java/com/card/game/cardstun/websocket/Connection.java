package com.card.game.cardstun.websocket;


import com.alibaba.fastjson2.JSON;
import com.card.game.cardstun.config.ConfiguratorForClientIp;
import com.card.game.cardstun.model.Message;
import com.card.game.cardstun.service.CommandService;
import com.card.game.cardstun.service.ForwardMessageService;
import com.card.game.cardstun.service.RoomService;
import com.card.game.common.constants.CommandType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: WebSocketServer
 * @Description: @ServerEndpoint不是单例模式
 * @auther: cunzhiwang
 */
@ServerEndpoint(value = "/websocket",configurator = ConfiguratorForClientIp.class)
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

    private String peerID;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        ip = (String) session.getUserProperties().get("clientIp");
        //未进任何房间时，将本次连接放到大厅里面
        roomService.enterLobby(this);
        log.info("用户:"+ip+"连接到服务器,当前在线人数为" + onlineCount.incrementAndGet());
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
        log.info("用户:"+ip+"关闭连接，退出房间"+roomId+"当前在线人数为" + onlineCount.addAndGet(-1));
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
     * @param stringMessage 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(Session session, String stringMessage) {
        Message message = JSON.parseObject(stringMessage, Message.class);
        log.info("收到来自"+ip+"的信息:"+message);
        if(CommandType.isExist(message.getCommand())){
            CommandType command = CommandType.getCommand(message.getCommand());
            switch (command) {
            case TYPE_COMMAND_ROOM_ENTER:
                this.userId = message.getUserId();
                this.roomId = message.getRoomId();
                enterRoom(message);
//                //服务器主动向所有在线的人推送房间列表
//                pushRoomList();
                break;
            case TYPE_COMMAND_DIALOGUE:
                forwardMessageService.sendMessageForEveryInRoom(message);
                break;//前端从服务器拉取房间列表
            case TYPE_COMMAND_READY:
            case TYPE_COMMAND_OFFER:
            case TYPE_COMMAND_ANSWER:
            case TYPE_COMMAND_CANDIDATE:
                forwardMessageService.sendMessageForEveryExcludeSelfInRoom(message);
                break;
            case TYPE_COMMAND_CREATE:
                createRoom(message);
                break;
            default:
                pullRoomList(message);
        }
        }else {
            message.setRoomId(null);
            message.setCode("500");
            message.setMessage("命令输入错误");
            sentMessage(message);
        }
    }

    private void  enterRoom(Message message) {
         message = roomService.enterRoom(roomId, this);
        //返回给自己是加入房间还是创建房间
        sentMessage(message);
    }

    private void pullRoomList(Message message) {
        message.setMessage(JSON.toJSONString(roomService.queryAllRoomName()));
        sentMessage(message);
    }

    private void createRoom(Message message){
        if(StringUtils.isBlank(message.getPeerID())){
            message.setMessage("peerID不能为空");
            message.setMessage("500");
        }else {
            message.setRoomId(roomService.createRoom(this,message));
            message.setMessage("房间创建成功");
            message.setCode("200");
        }
        sentMessage(message);
    }

    private void sentMessage(Message message) {
        try {
            session.getBasicRemote().sendText(JSON.toJSONString(message));
        } catch (IOException e) {
           log.info("发送失败");
        } finally {
            try {
                session.close();
            } catch (IOException e) {
              log.info("关闭失败");
            }
        }
    }

    private void pushRoomList() {
        //告诉每个终端更新房间列表
        Message roomListMessage = new Message();
        roomListMessage.setCommand(CommandType.TYPE_COMMAND_ROOM_LIST.getType());
        roomListMessage.setMessage(JSON.toJSONString(roomService.queryAllRoomName()));
        forwardMessageService.sendMessageForAllOnline(roomListMessage);
    }
}
