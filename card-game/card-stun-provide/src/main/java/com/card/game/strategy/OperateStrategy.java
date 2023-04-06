package com.card.game.strategy;

import com.card.game.model.ConnectionEntity;
import com.card.game.model.MessageEntity;

public interface OperateStrategy {
    void operate(MessageEntity message, ConnectionEntity connection);

}
