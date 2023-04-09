package com.card.game.oatuh.api;

import com.card.game.api.user.dto.SysUserDTO;
import com.card.game.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "card-game")
public interface UserService {

    @GetMapping("/user/mail/isUserRegistered/{mailAccount}")
    Result<Boolean> getByUsername(@PathVariable(value = "mailAccount") String mailAccount);
    @GetMapping("/user/getUserByMailAccount")
    Result<SysUserDTO> getUserByMailAccount(@RequestParam("account") String account);
}
