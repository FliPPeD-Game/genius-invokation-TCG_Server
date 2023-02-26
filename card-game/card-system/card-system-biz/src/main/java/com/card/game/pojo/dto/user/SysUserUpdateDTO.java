package com.card.game.pojo.dto.user;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SysUserUpdateDTO {

    @NotBlank(message = "用户昵称不能为空")
    @Length(min = 3, max = 10)
    private String nickname;

    @NotBlank(message = "邮箱地址不能为空")
    @Email(message = "错误的邮箱类型")
    private String email;

    private Integer gender;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16)
    private String password;

    private String rePassword;

}
