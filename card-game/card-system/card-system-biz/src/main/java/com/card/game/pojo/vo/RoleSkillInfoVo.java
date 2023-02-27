package com.card.game.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleSkillInfoVo {
    private Long id;
    private String name;
    private String resource;
    private String skill_text;
    private String type;
    private List<SkillCostVo> costs;
}
