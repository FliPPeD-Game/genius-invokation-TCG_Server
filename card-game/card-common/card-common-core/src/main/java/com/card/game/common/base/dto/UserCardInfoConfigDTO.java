package com.card.game.common.base.dto;

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
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 用户卡牌配置
 *
 * @author cunzhiwang
 * @Date 2023/3/2 17:19
 */
@Data
public class UserCardInfoConfigDTO {

    private Long userId;

    private String roleId;

    private String actionId;

    private Integer flag;

    private Integer isDefault;
}
