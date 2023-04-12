package com.card.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.card.game.common.base.entity.RoleCardInfoEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleCardInfoMapper extends BaseMapper<RoleCardInfoEntity> {
    List<RoleCardInfoEntity> getRoleCardInfos(@Param("ids") List<Long> ids);
}
