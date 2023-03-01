package com.card.game.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class CardInfoVO {
    List<RoleCardInfoVO> roleCardInfos;
    List<ActionCardInfoVO> actionCardInfos;
}
