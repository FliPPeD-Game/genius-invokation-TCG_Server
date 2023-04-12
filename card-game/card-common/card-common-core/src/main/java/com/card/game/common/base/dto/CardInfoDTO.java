package com.card.game.common.base.dto;

import java.util.List;
import lombok.Data;

@Data
public class CardInfoDTO {
    List<RoleCardInfoDTO> roleCardInfos;
    List<ActionCardInfoDTO> actionCardInfos;
}
