package com.card.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 表格数据同步配置
 *
 * @author cunzhiwang
 * @Date 2023/2/15 15:31
 */
@Data
@TableName("sys_attribute_sync_config")
@ApiModel(value = "SysAttributeSyncInfo对象", description = "")
public class SysAttributeSyncEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("数据库名称")
    private String database;

    @ApiModelProperty("对应mapper1路径")
    private String mapper1;

    @ApiModelProperty("对应mapper2路径")
    private String mapper2;


    @ApiModelProperty("表一属性")
    private String t1Attribute;

    @ApiModelProperty("表二属性")
    private String t2Attribute;

}
