package com.card.game.common.base.dto;


import lombok.Data;

@Data
public class ActionCardInfoDTO {

    private Long id;

    private String actionCardTags;

    private String actionType;

    private String resource;

    private String content;

    private Integer costNum1;

    private Integer costNum2;

    private String costType1Icon;

    private String costType2Icon;

    private String name;

    private Integer rankId;
}
