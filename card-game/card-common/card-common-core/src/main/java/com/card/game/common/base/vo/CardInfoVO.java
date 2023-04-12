package com.card.game.common.base.vo;

import lombok.Data;

import java.util.List;

@Data
public class CardInfoVO {
    List<RoleCardInfoVO> roleCardInfos;
    List<ActionCardInfoVO> actionCardInfos;
}
