package com.card.game.cardstun.constants;

import java.util.Arrays;

import com.card.game.cardstun.model.Message;
import com.card.game.cardstun.websocket.Connection;
import lombok.Getter;

/**
 * 命令枚举
 *
 * @author cunzhiwang
 * @Date 2023/3/7 10:49
 */
@Getter
public enum CommandType {
    TYPE_COMMAND_ROOM_ENTER("enterRoom"){
        @Override
        public Message operate(Message message, Connection connection) {
            return null;
        }
    },
    TYPE_COMMAND_ROOM_LIST("roomList") {
        @Override
        public Message operate(Message message, Connection connection) {
            return null;
        }
    },
    TYPE_COMMAND_DIALOGUE("dialogue") {
        @Override
        public Message operate(Message message, Connection connection) {
            return null;
        }
    },
    TYPE_COMMAND_READY("ready") {
        @Override
        public Message operate(Message message, Connection connection) {
            return null;
        }
    },
    TYPE_COMMAND_OFFER("offer") {
        @Override
        public Message operate(Message message, Connection connection) {
            return null;
        }
    },
    TYPE_COMMAND_ANSWER("answer") {
        @Override
        public Message operate(Message message, Connection connection) {
            return null;
        }
    },
    TYPE_COMMAND_CANDIDATE("candidate") {
        @Override
        public Message operate(Message message, Connection connection) {
            return null;
        }
    },
    TYPE_COMMAND_CREATE("createRoom") {
        @Override
        public Message operate(Message message, Connection connection) {
            return null;
        }
    };
    private final String type;

    CommandType(String type) {
        this.type = type;
    }
public abstract Message operate(Message message, Connection connection);
    public static boolean isExist(String type) {
        return Arrays.stream(CommandType.values()).anyMatch(e -> e.getType().equals(type));
    }

    public static CommandType getCommand(String type) {
        return Arrays.stream(CommandType.values()).filter(e -> e.getType().equals(type)).findFirst().get();
    }
}
