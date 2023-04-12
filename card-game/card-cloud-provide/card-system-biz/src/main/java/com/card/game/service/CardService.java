package com.card.game.service;

import com.card.game.common.base.vo.CardInfoVO;
import com.card.game.common.base.vo.UserCarInfoConfigVO;

public interface CardService {
    /**
     * 返回所有卡牌信息
     */
    CardInfoVO getAllCardInfo();

    UserCarInfoConfigVO getUserCardConfigInfo();
}
