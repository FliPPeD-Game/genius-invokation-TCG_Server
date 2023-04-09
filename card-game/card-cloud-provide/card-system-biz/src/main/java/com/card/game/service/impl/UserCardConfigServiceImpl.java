package com.card.game.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.card.game.aop.AopResult;
import com.card.game.api.user.dto.SysUserDTO;
import com.card.game.mapper.UserCardInfoConfigMapper;
import com.card.game.common.base.dto.UserCardInfoConfigDTO;
import com.card.game.common.base.entity.UserCardInfoConfigEntity;
import com.card.game.common.base.vo.ActionCardInfoVO;
import com.card.game.common.base.vo.CardInfoVO;
import com.card.game.common.base.vo.RoleCardInfoVO;
import com.card.game.common.base.vo.UserCarInfoConfigVO;
import com.card.game.security.support.userdetails.SecurityMailUserDetails;
import com.card.game.security.utils.SecurityContextUtils;
import com.card.game.service.ActionCardInfoService;
import com.card.game.service.RoleCardInfoService;
import com.card.game.service.UserCardConfigService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author cunzhiwang
 * @Date 2023/3/3 15:18
 */
@Service
@RequiredArgsConstructor
public class UserCardConfigServiceImpl extends
        ServiceImpl<UserCardInfoConfigMapper, UserCardInfoConfigEntity> implements
        UserCardConfigService {

    private final UserCardInfoConfigMapper cardInfoConfigMapper;

    private final RoleCardInfoService roleCardInfoService;

    private final ActionCardInfoService actionCardInfoService;


    @Override
    @AopResult
    public UserCarInfoConfigVO getUserAllCardInfo() {
        SecurityMailUserDetails currentUserInfo = SecurityContextUtils.getCurrentUserInfo();
        SysUserDTO userDTO = currentUserInfo.getSysUserDTO();
        List<UserCardInfoConfigDTO> userCardConfigs = cardInfoConfigMapper.getUserCardConfigs(userDTO.getId());
        UserCarInfoConfigVO userConfig = new UserCarInfoConfigVO();
        List<CardInfoVO> configs = new ArrayList<>();
        userCardConfigs.forEach(config -> {
            CardInfoVO userCardInfo = new CardInfoVO();
            String roleId = config.getRoleId();
            List<Long> roleCardIds = Arrays.stream(roleId.split(",")).map(Long::valueOf).collect(Collectors.toList());
            List<RoleCardInfoVO> roleCardInfos = roleCardInfoService.getRoleCardInfos(roleCardIds);
            String actionId = config.getActionId();
            List<Long> actionCardIds = Arrays.stream(actionId.split(",")).map(Long::valueOf)
                    .collect(Collectors.toList());
            List<ActionCardInfoVO> actionCardInfos = actionCardInfoService.getActionCardInfos(actionCardIds);
            userCardInfo.setActionCardInfos(actionCardInfos);
            userCardInfo.setRoleCardInfos(roleCardInfos);
            configs.add(userCardInfo);
        });
        userConfig.setIndex(0);
        userConfig.setConfigs(configs);
        return userConfig;
    }
}
