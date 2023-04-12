package com.card.game.controller;

import com.card.game.api.user.SysUserApiImpl;
import com.card.game.api.user.dto.SysUserDTO;
import com.card.game.common.base.dto.user.SysUserUpdateDTO;
import com.card.game.api.user.vo.SysUserVO;
import com.card.game.common.result.Result;
import com.card.game.common.base.dto.EmailRegisterDTO;
import com.card.game.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 用户控制器
 * </p>
 *
 * @author tom
 * @since 2023-01-08
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;
    private final SysUserApiImpl sysUserApi;

//    @GetMapping("/getUserInfo")
//    public Result<SecurityMailUserDetails> getUserInfo() {
//        SecurityMailUserDetails principal = (SecurityMailUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return Result.success(principal);
//    }

    @GetMapping("/getUserByMailAccount")
    public Result<SysUserDTO> getUserByMailAccount(@RequestParam("account") String account){
       return Result.success(sysUserApi.getUserByMailAccount(account));
    }

    @PostMapping("/mail/registerUser")
    public Result<Map<String, Object>> registerUser(@RequestBody @Validated EmailRegisterDTO emailRegisterDTO) {
        Map<String, Object> userInfo = sysUserService.registerUser(emailRegisterDTO);
        return Result.success(userInfo);
    }

    @GetMapping("/mail/isUserRegistered/{mailAccount}")
    public Result<Boolean> isUserRegisteredByMailAccount(@PathVariable String mailAccount) {
        Boolean flag = sysUserService.isUserRegisteredByMailAccount(mailAccount);
        return Result.success(flag);
    }


    @PostMapping("/updateUserInfo")
    public Result<SysUserVO> updateUSerInfo(@RequestBody @Validated SysUserUpdateDTO sysUserUpdateDTO) {
        SysUserVO sysUserVO = sysUserService.updateUserInfo(sysUserUpdateDTO);
        return Result.success(sysUserVO);
    }
}
