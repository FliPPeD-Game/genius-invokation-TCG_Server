package com.card.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.card.game.common.base.entity.ActionCardInfoEntity;
import com.card.game.common.base.vo.ActionCardInfoVO;

import java.util.List;

public interface ActionCardInfoService extends IService<ActionCardInfoEntity> {

    /**
     * 添加动作卡牌
     * @param url 链接
     * @return 是否添加成功
     */

    boolean addActionCardInfo(String url);

    /**
     * 根据集合id返回行为卡牌
     * @param ids 为null表示返回全部信息
     * @return 行为卡牌信息
     */
    List<ActionCardInfoVO> getActionCardInfos(List<Long> ids);
}
