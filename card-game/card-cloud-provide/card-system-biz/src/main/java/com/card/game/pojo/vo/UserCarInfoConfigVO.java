package com.card.game.pojo.vo;

import com.card.game.pojo.vo.CardInfoVO;
import java.util.List;
import lombok.Data;

/**
 * 当前用户所有配置信息
 *
 * @author cunzhiwang
 * @Date 2023/3/3 15:43
 */
@Data
public class UserCarInfoConfigVO {
    // 当前配置下标
    Integer index;

    List<CardInfoVO> configs;

}
