package com.card.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

/**
 * 表格数据同步配置
 *
 * @author cunzhiwang
 * @Date 2023/2/15 15:31
 */
public class SysAttributeSyncEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("数据库名称")
    private String database;

    @ApiModelProperty("图片路径")
    private String table1;

    @ApiModelProperty("城市")
    private String table2;


    @ApiModelProperty("表一属性")
    private String t1Attribute;

    @ApiModelProperty("表二属性")
    private String t2Attribute;

}
