package com.card.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.card.game.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户卡牌配置
 *
 * @author cunzhiwang
 * @Date 2023/3/2 17:19
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_card_info_config")
public class UserCardInfoConfigEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private String roleId;

    private String actionId;

    private Integer flag;

    private Integer isDefault;
}
