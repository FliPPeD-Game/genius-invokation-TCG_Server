package com.card.game.strategy;

import com.card.game.model.ConnectionEntity;
import com.card.game.model.MessageEntity;
import lombok.RequiredArgsConstructor;

/**
 * 上下文
 *
 * @author cunzhiwang
 * @Date 2023/4/4 11:46
 */
@RequiredArgsConstructor
public class Context {

    private final OperateStrategy operateStrategy;

    public void operate(MessageEntity message, ConnectionEntity connection) {
        operateStrategy.operate(message, connection);
    }
}
