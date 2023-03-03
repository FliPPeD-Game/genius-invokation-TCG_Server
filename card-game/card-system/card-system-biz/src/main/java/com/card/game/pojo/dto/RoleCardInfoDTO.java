package com.card.game.pojo.dto;

import java.util.List;
import lombok.Data;

@Data
public class RoleCardInfoDTO {
    private Long id;
    private String name;
    private Integer elementType;
    private Integer hp;
    private String belongs;
    private String resource;
    private String weapon;
    List<RoleSkillInfoDTO> skillInfoVos;

}
