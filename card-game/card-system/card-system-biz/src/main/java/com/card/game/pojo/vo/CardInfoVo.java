package com.card.game.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class CardInfoVo {
    private Long id;
    private String name;
    private Integer element_type;
    private Integer hp;
    private String belongs;
    private String resource;
    private String weapon;
    List<RoleSkillInfoVo> skillInfoVos;

}
