package com.card.game.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.card.game.mapper.SysAttributeSyncConfigMapper;
import com.card.game.pojo.entity.SysAttributeSyncEntity;
import com.card.game.service.SysAttributeSyncService;
import org.springframework.stereotype.Service;

/**
 * 数据同步服务类
 *
 * @author cunzhiwang
 * @Date 2023/2/16 17:05
 */
@Service
public class SysAttributeSyncServiceImpl extends
        ServiceImpl<SysAttributeSyncConfigMapper, SysAttributeSyncEntity> implements
        SysAttributeSyncService {

}
