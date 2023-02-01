package com.card.game.cardstun.service;

import com.card.game.cardstun.websocket.Connection;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @ClassName: RoomService
 * @Description: websocket房间服务
 * @auther: cunzhiwang
 * @date: 2019/8/17 18:05
 */
@Service
public class RoomService {

    private Map<String,Set<Connection>> rooms = new ConcurrentHashMap<>();


    /**
     * 加入到大厅
     */
    public void enterLobby(Connection connection) {
        Set<Connection> lobby =rooms.get("lobby");
        if(lobby == null){
            rooms.put("lobby", new HashSet<>());
            lobby =rooms.get("lobby");
            lobby.add(connection);
        }else {
            lobby.add(connection);
        }
    }

    /**
     * 离开大厅
     */
    public void leaveLobby(Connection connection) {
        System.out.println(connection);
        Set<Connection> lobby =rooms.get("lobby");
        lobby.remove(connection);
    }

    /**
     * 加入指定的房间
     */
    public String enterRoom(String roomId, Connection connection) {

        String operate;
        Set<Connection> room =rooms.get(roomId);
        if(room == null){
            rooms.put(roomId, new HashSet<>());
            room =rooms.get(roomId);
            room.add(connection);
            operate = "created";
        }else {
            room.add(connection);
            operate = "joined";
        }
        //离开大厅
        leaveLobby(connection);
        return operate;
    }

    /**
     * 离开指定的房间
     */
    public void leaveRoom(String roomId, Connection connection) {
        if (roomId != null) {
            Set<Connection> room = rooms.get(roomId);
            if (room != null) {
                room.remove(connection);
                if (room.size() == 0) {
                    rooms.remove(roomId);
                }
            }
        }

    }

    /**
     * 查询指定房间人数（包括自己）
     */
    public Integer queryCountInRoom( String roomId){
        Set<Connection> room = rooms.get(roomId);
        if (room == null) {
            return 0;
        } else {
            return room.size();
        }

    }

    /**
     * 将用户踢出房间
     */
    public void removeUserFromRoom( String roomId, String userId){
        Set<Connection> room = rooms.get(roomId);
        if (room != null) {
            room.removeIf(user -> user.getUserId().equals(userId));
        }

    }

    /**
     * 通过房间Id查询房间
     */
    public Set<Connection> queryRoomById(String roomId){

        return rooms.get(roomId);
    }

    /**
     * 查询所有存在的房间名称
     */
    public Set<String> queryAllRoomName(){
        return rooms.keySet();
    }

    /**
     * 查询所有存在的房间
     */
    public Collection<Set<Connection>> queryAllRoom(){
        return rooms.values();
    }

}
