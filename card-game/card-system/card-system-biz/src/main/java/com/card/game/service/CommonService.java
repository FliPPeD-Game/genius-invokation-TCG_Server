package com.card.game.service;


import com.card.game.pojo.vo.CardInfoVo;

import java.util.List;

public interface CommonService {


    /**
     *添加卡牌信息
     */
    boolean addRoleCardInfo(String url);

    /**
     * 返回所有卡牌信息
     */
    public List<CardInfoVo> getAllCardInfo();
}
