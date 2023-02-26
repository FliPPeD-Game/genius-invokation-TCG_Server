package com.card.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("role_skill_infos")
@Data
public class RoleSkillInfoEntity {
    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("属于")
    private String belongs;

    @ApiModelProperty("技能类型")
    private String type;


    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("图片路径")
    private String resource;

    @ApiModelProperty("技能描述")
    private String skillText;
}
