package com.card.game.service.impl;

import com.card.game.pojo.vo.CardInfoVO;
import com.card.game.pojo.vo.UserCarInfoConfigVO;
import com.card.game.service.ActionCardInfoService;
import com.card.game.service.CardService;
import com.card.game.service.RoleCardInfoService;
import com.card.game.service.UserCardConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final RoleCardInfoService roleCardInfoService;
    private final ActionCardInfoService actionCardInfoService;

    private final UserCardConfigService userCardConfigService;
    @Override
    public CardInfoVO getAllCardInfo() {
        val allRoleCardInfo = roleCardInfoService.getRoleCardInfos(null);
        val allActionCardInfo = actionCardInfoService.getActionCardInfos(null);
        CardInfoVO cardInfoVO = new CardInfoVO();
        cardInfoVO.setActionCardInfos(allActionCardInfo);
        cardInfoVO.setRoleCardInfos(allRoleCardInfo);
        return cardInfoVO;
    }

    @Override
    public UserCarInfoConfigVO getUserCardConfigInfo() {
        return userCardConfigService.getUserAllCardInfo();
    }
}
