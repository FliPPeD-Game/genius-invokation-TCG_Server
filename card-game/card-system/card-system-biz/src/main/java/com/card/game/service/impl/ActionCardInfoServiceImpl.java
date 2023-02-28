package com.card.game.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.card.game.mapper.ActionCardInfoMapper;
import com.card.game.pojo.entity.ActionCardInfoEntity;
import com.card.game.service.ActionCardInfoService;
import org.springframework.stereotype.Service;

@Service
public class ActionCardInfoServiceImpl extends ServiceImpl<ActionCardInfoMapper, ActionCardInfoEntity> implements ActionCardInfoService {
}
