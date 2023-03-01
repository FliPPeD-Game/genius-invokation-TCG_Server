package com.card.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.card.game.pojo.entity.RoleCardInfoEntity;
import com.card.game.pojo.vo.RoleCardInfoVO;

import java.util.List;

public interface RoleCardInfoService extends IService<RoleCardInfoEntity> {

    /**
     * 返回所有角色卡牌信息
     */
    List<RoleCardInfoVO> getAllRoleCardInfo();

    /**
     * 添加对应角色卡牌信息
     * @param url 对应url
     * @return 是否成功
     */
    boolean addRoleCardInfo(String url);

}
