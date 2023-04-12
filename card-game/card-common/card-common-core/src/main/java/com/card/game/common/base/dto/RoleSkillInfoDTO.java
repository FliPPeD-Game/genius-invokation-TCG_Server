package com.card.game.common.base.dto;

import java.util.List;
import lombok.Data;

@Data
public class RoleSkillInfoDTO {
    private Long id;
    private String name;
    private String resource;
    private String skillText;
    private String type;
    private List<SkillCostDTO> costs;
}
