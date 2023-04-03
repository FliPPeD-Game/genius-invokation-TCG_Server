package com.card.game.service;

import com.card.game.pojo.vo.CardInfoVO;
import com.card.game.pojo.vo.UserCarInfoConfigVO;
import java.util.List;

public interface CardService {
    /**
     * 返回所有卡牌信息
     */
    CardInfoVO getAllCardInfo();

    UserCarInfoConfigVO getUserCardConfigInfo();
}
