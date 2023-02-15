package com.card.game.common.constants;

import java.util.Arrays;
import lombok.Getter;

/**
 * 任务类型
 *
 * @author cunzhiwang
 * @Date 2023/2/15 14:52
 */
@Getter
public enum TaskType {
    // 属性同步定时任务
    AttributeSync("AttributeSync");
    private final String type;

    TaskType(String type) {
        this.type = type;
    }

    public static boolean isExist(String type) {
        return Arrays.stream(TaskType.values()).anyMatch(e -> e.getType().equals(type));
    }
}
