package com.card.game.common.base.dto.user;


import com.card.game.api.user.dto.AvatarDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


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
