package com.card.game.common.base.dto.user;


import com.card.game.api.user.dto.AvatarDTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class SysUserUpdateDTO {

    @NotBlank(message = "用户昵称不能为空")
    @Length(min = 3, max = 10)
    private String nickname;

    private Integer gender;

    private String password;

    private String rePassword;

    private AvatarDTO avatarInfo;
}
