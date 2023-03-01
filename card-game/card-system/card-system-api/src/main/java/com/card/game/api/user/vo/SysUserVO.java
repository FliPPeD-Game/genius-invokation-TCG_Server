package com.card.game.api.user.vo;

import com.card.game.api.user.dto.AvatarDTO;
import lombok.Data;

/**
 * @author tomyou
 * @version v1.0 2023-01-08-4:52 PM
 */
@Data
public class SysUserVO {

    /**
     * 用户昵称
     */
    private String nickname;


    /**
     * 用户性别((1:男;0:女,2:未知))
     */
    private Integer gender;

    /**
     * 用户头像信息
     */
    private AvatarDTO avatarInfo;

    /**
     * 用户邮箱帐号
     */
    private String email;


    /**
     * 0-正常，1-锁定
     */
    private Boolean lockFlag;


}
