package com.card.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.card.game.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 图片信息实体
 *
 * @author cunzhiwang
 * @Date 2023/1/17 10:41
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_imageInfo")
@ApiModel(value = "SysImageInfo对象", description = "")
public class SysImageInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("图片名称")
    private String name;

    @ApiModelProperty("图片路径")
    private String src;

    @ApiModelProperty("城市")
    private String country;
}
