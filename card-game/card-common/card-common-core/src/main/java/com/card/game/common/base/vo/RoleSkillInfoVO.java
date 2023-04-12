package com.card.game.common.base.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleSkillInfoVO {
    private Long id;
    private String name;
    private String resource;
    private String skillText;
    private String type;
    private List<SkillCostVO> costs;
}
