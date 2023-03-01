package com.card.game.pojo.dto.user;


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

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16)
    private String password;

    private String rePassword;

    private AvatarDTO avatarInfo;
}
