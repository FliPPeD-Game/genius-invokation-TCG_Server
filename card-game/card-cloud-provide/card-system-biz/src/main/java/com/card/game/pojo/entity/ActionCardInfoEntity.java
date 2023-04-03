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

@TableName("action_card_info")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionCardInfoEntity extends BaseEntity {
    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("技能类型")
    private String actionCardTags;

    @ApiModelProperty("动作牌类型")
    private String actionType;

    @ApiModelProperty("图片路径")
    private String resource;

    @ApiModelProperty("技能描述")
    private String content;

    @ApiModelProperty("")
    private Integer costNum1;

    @ApiModelProperty("")
    private Integer costNum2;

    @ApiModelProperty("")
    private String costType1Icon;

    @ApiModelProperty("")
    private String costType2Icon;

    @ApiModelProperty("技能名称")
    private String name;

    @ApiModelProperty("")
    private Integer rankId;

}
