package com.card.game.flyweight;

import com.card.game.model.ConnectionEntity;

import java.util.Set;

public class ConnectionFlyweight implements IFlyweight {

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

    @Override
    public Set<ConnectionEntity> queryAll() {
        return room;
    }
}
