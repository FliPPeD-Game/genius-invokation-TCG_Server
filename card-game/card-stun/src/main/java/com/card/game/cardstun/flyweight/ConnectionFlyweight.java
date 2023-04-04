package com.card.game.cardstun.flyweight;

import com.card.game.cardstun.model.ConnectionEntity;
import com.card.game.cardstun.websocket.Connection;

import java.util.HashSet;
import java.util.Set;

public class ConnectionFlyweight implements IFlyweight {

    private final Set<ConnectionEntity> room=new HashSet<>();

    @Override
    public void join(ConnectionEntity connection) {
       room.add(connection);
    }

    @Override
    public boolean delete(ConnectionEntity connection) {
        return room.remove(connection);
    }

    @Override
    public Integer query() {
        return room.size();
    }
}
