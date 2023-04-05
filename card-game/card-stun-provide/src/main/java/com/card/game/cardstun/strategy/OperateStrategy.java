package com.card.game.cardstun.strategy;

import com.card.game.cardstun.model.ConnectionEntity;
import com.card.game.cardstun.model.MessageEntity;

public interface OperateStrategy {
    void operate(MessageEntity message, ConnectionEntity connection);

}
