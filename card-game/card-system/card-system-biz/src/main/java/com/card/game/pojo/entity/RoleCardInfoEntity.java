package com.card.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.card.game.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("role_card_infos")
@Data
public class RoleCardInfoEntity extends BaseEntity {
    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("属于")
    private String belongs;

    @ApiModelProperty("元素类型")
    private Integer elementType;

    @ApiModelProperty("生命值")
    private Integer hp;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("图片路径")
    private String resource;

    @ApiModelProperty("武器")
    private String weapon;


}
