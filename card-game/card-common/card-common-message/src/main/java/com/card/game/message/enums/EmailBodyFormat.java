
package com.card.game.message.enums;
/**
 * @author tomyou
 * @version v1.0 2023-01-15-9:27 PM
 */
public enum EmailBodyFormat {
    TEXT(0),
    HTML(1);

    private int value;

    private EmailBodyFormat(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }


}
