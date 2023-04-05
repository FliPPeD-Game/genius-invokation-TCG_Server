package com.card.game.cardstun.constants;

import com.card.game.cardstun.strategy.OperateStrategy;
import com.card.game.cardstun.strategy.impl.DialogueStrategy;
import com.card.game.cardstun.strategy.impl.EnterRoomStrategy;

import java.util.Arrays;

import com.card.game.cardstun.strategy.impl.RoomListStrategy;
import lombok.Getter;

/**
 * 命令枚举
 *
 * @author cunzhiwang
 * @Date 2023/3/7 10:49
 */
@Getter
public enum CommandType {
    TYPE_COMMAND_ROOM_ENTER("enterRoom", EnterRoomStrategy.class),
    TYPE_COMMAND_ROOM_LIST("roomList", RoomListStrategy.class),
    TYPE_COMMAND_DIALOGUE("dialogue", DialogueStrategy.class),
    TYPE_COMMAND_READY("ready", null),
    TYPE_COMMAND_OFFER("offer", null),
    TYPE_COMMAND_ANSWER("answer", null),
    TYPE_COMMAND_CANDIDATE("candidate", null),
    TYPE_COMMAND_CREATE("createRoom", null);
    private final String command;
    private final Class<? extends OperateStrategy> type;

    CommandType(String command, Class<? extends OperateStrategy> type) {
        this.command = command;
        this.type = type;
    }

    public static boolean isExist(String command) {
        return Arrays.stream(CommandType.values()).anyMatch(e -> e.getCommand().equals(command));
    }

    public static CommandType getCommand(String command) {
        return Arrays.stream(CommandType.values()).filter(e -> e.getCommand().equals(command)).findFirst().get();
    }
}
