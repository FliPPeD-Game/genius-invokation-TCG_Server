package com.card.game.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.card.game.mapper.SkillCostMapper;
import com.card.game.common.base.entity.SkillCostEntity;
import com.card.game.service.SkillCostService;
import org.springframework.stereotype.Service;

@Service
public class SkillCostServiceImpl extends ServiceImpl<SkillCostMapper, SkillCostEntity>  implements SkillCostService {
}
