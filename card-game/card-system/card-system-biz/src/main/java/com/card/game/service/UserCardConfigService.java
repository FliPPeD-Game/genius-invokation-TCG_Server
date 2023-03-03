package com.card.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.card.game.pojo.vo.UserCarInfoConfigVO;
import com.card.game.pojo.entity.UserCardInfoConfigEntity;
import java.util.List;

/**
 *
 *
 * @author cunzhiwang
 * @Date 2023/3/3 15:16
 */
public interface UserCardConfigService extends IService<UserCardInfoConfigEntity> {
    UserCarInfoConfigVO getUserAllCardInfo();
}
