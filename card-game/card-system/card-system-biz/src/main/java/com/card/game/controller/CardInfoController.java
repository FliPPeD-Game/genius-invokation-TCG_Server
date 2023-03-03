package com.card.game.controller;

import com.card.game.common.result.Result;
import com.card.game.pojo.vo.ActionCardInfoVO;
import com.card.game.pojo.vo.CardInfoVO;
import com.card.game.pojo.vo.RoleCardInfoVO;
import com.card.game.pojo.vo.UserCarInfoConfigVO;
import com.card.game.service.ActionCardInfoService;
import com.card.game.service.CardService;
import com.card.game.service.RoleCardInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/card/info")
@RequiredArgsConstructor
public class CardInfoController {

    private final RoleCardInfoService roleCardInfoService;

    private final ActionCardInfoService actionCardInfoService;

    private final CardService cardService;

    @GetMapping("/addRoleCardInfo")
    public Result<Boolean> addRoleCardInfo(@RequestParam("url") String url) {
        return Result.success(roleCardInfoService.addRoleCardInfo(url));
    }

    @GetMapping("/addActionCardInfo")
    public Result<Boolean> addActionCardInfo(@RequestParam("url") String url) {
        return Result.success(actionCardInfoService.addActionCardInfo(url));
    }

    @GetMapping("/getAllRoleCardInfo")
    public Result<List<RoleCardInfoVO>> getAllRoleCardInfo() {
        return Result.success(roleCardInfoService.getRoleCardInfos(null));
    }

    @GetMapping("/getAllActionCardInfo")
    public Result<List<ActionCardInfoVO>> getAllActionCardInfo() {
        return Result.success(actionCardInfoService.getActionCardInfos(null));
    }

    @GetMapping("/getAllCardInfo")
    public Result<CardInfoVO> getAllCardInfo() {
        return Result.success(cardService.getAllCardInfo());
    }

    @GetMapping("/getUserCardInfoConfig")
    public Result<UserCarInfoConfigVO> getUserCardInfoConfig() {
        return Result.success(cardService.getUserCardConfigInfo());
    }
}
