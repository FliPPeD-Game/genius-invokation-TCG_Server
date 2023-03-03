package com.card.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.card.game.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@TableName("role_skill_infos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleSkillInfoEntity extends BaseEntity {
    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("技能类型")
    private String type;


    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("图片路径")
    private String resource;

    @ApiModelProperty("技能描述")
    private String skillText;
}
