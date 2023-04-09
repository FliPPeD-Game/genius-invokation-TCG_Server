package com.card.game.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.card.game.mapper.RoleSkillInfoMapper;
import com.card.game.common.base.entity.RoleSkillInfoEntity;
import com.card.game.service.RoleSkillInfoService;
import org.springframework.stereotype.Service;

@Service
public class RoleSkillInfoServiceImpl extends ServiceImpl<RoleSkillInfoMapper, RoleSkillInfoEntity> implements RoleSkillInfoService {
}
