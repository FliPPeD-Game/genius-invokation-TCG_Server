package com.card.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.card.game.pojo.entity.ActionCardInfoEntity;
import com.card.game.pojo.vo.ActionCardInfoVO;

import java.util.List;

public interface ActionCardInfoService extends IService<ActionCardInfoEntity> {

    /**
     * 添加动作卡牌
     * @param url 链接
     * @return 是否添加成功
     */

    boolean addActionCardInfo(String url);

    List<ActionCardInfoVO> getAllActionCardInfo();
}
