package com.card.game.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.card.game.mapper.RoleCardInfoMapper;
import com.card.game.pojo.entity.RoleCardInfoEntity;
import com.card.game.service.RoleCardInfoService;
import org.springframework.stereotype.Service;

@Service
public class RoleCardInfoServiceImpl extends ServiceImpl<RoleCardInfoMapper, RoleCardInfoEntity> implements RoleCardInfoService {
}
