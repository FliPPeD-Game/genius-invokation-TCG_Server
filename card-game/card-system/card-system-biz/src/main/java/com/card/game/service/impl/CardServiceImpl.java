package com.card.game.service.impl;

import com.card.game.pojo.vo.CardInfoVO;
import com.card.game.service.ActionCardInfoService;
import com.card.game.service.CardService;
import com.card.game.service.RoleCardInfoService;
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
    @Override
    public CardInfoVO getAllCardInfo() {
        val allRoleCardInfo = roleCardInfoService.getAllRoleCardInfo();
        val allActionCardInfo = actionCardInfoService.getAllActionCardInfo();
        CardInfoVO cardInfoVO = new CardInfoVO();
        cardInfoVO.setActionCardInfos(allActionCardInfo);
        cardInfoVO.setRoleCardInfos(allRoleCardInfo);
        return null;
    }
}
