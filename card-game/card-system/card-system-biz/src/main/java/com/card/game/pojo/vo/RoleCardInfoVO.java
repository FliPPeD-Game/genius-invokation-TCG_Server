package com.card.game.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleCardInfoVO {
    private Long id;
    private String name;
    private Integer elementType;
    private Integer hp;
    private String belongs;
    private String resource;
    private String weapon;
    List<RoleSkillInfoVO> skillInfoVos;

}
