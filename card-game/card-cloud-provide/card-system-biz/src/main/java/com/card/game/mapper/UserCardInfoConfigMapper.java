package com.card.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.card.game.common.base.dto.UserCardInfoConfigDTO;
import com.card.game.common.base.entity.UserCardInfoConfigEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 *
 * @author cunzhiwang
 * @Date 2023/3/3 15:00
 */
public interface UserCardInfoConfigMapper extends BaseMapper<UserCardInfoConfigEntity> {

    List<UserCardInfoConfigDTO> getUserCardConfigs(@Param("userID")Long userId);
}
