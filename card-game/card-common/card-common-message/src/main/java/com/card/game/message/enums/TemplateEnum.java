package com.card.game.message.enums;

import com.card.game.common.base.IBaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author tomyou
 * @version v1.0 2023-01-15-10:26 PM
 */
@AllArgsConstructor
public enum TemplateEnum implements IBaseEnum<String> {
    REGISTER_TEMPLATE("注册邮件模版", "Register");

    private final String templateName;

    private final String templatePath;

    @Override
    public String getValue() {
        return this.templatePath;
    }

    @Override
    public String getLabel() {
        return this.templateName;
    }
}
