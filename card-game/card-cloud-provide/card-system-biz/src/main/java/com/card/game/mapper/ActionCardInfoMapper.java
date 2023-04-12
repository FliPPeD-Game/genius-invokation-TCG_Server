package com.card.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.card.game.common.base.entity.ActionCardInfoEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActionCardInfoMapper extends BaseMapper<ActionCardInfoEntity> {
    List<ActionCardInfoEntity> getActionCardInfos(@Param("ids") List<Long> ids);
}
