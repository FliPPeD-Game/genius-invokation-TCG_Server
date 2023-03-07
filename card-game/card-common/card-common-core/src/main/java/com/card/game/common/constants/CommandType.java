package com.card.game.common.constants;

import java.util.Arrays;
import lombok.Getter;

/**
 * 命令枚举
 *
 * @author cunzhiwang
 * @Date 2023/3/7 10:49
 */
@Getter
public enum CommandType {
    TYPE_COMMAND_ROOM_ENTER("enterRoom"),
    TYPE_COMMAND_ROOM_LIST("roomList"),
    TYPE_COMMAND_DIALOGUE("dialogue"),
    TYPE_COMMAND_READY("ready"),
    TYPE_COMMAND_OFFER("offer"),
    TYPE_COMMAND_ANSWER("answer"),
    TYPE_COMMAND_CANDIDATE("candidate"),
    TYPE_COMMAND_CREATE("createRoom");
    private final String type;

    CommandType(String type) {
        this.type = type;
    }

    public static boolean isExist(String type) {
        return Arrays.stream(CommandType.values()).anyMatch(e -> e.getType().equals(type));
    }

    public static CommandType getCommand(String type) {
        return Arrays.stream(CommandType.values()).filter(e -> e.getType().equals(type)).findFirst().get();
    }
}
