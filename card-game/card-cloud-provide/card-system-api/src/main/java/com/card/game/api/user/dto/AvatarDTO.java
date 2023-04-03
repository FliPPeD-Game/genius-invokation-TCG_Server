package com.card.game.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tomyou
 * @version v1.0 2023/2/26 15:47
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDTO {

    /**
     * 头像地址
     */
    private String url;

    /**
     * 国家
     */
    private String country;
}
