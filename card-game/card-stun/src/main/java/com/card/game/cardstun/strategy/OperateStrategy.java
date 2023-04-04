package com.card.game.cardstun.strategy;

import com.card.game.cardstun.model.ConnectionEntity;
import com.card.game.cardstun.model.Message;

public interface OperateStrategy {
    void operate(Message message, ConnectionEntity connection);

}
