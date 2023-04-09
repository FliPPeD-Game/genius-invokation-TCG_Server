package com.card.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.card.game.common.base.entity.SysImageInfoEntity;

/**
 * SysImageInfoMapper
 *
 * @author cunzhiwang
 * @Date 2023/1/17 10:47
 */
public interface SysImageInfoMapper extends BaseMapper<SysImageInfoEntity> {

    SysImageInfoEntity getRandomAvatar();

}
