package com.card.game.controller;

import com.card.game.common.result.Result;
import com.card.game.message.MessageManager;
import com.card.game.message.pojo.mail.MailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;

/**
 * @author tomyou
 * @version 1.0 created on 2023/1/16 9:26
 */
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Validated
public class MessageController {

    private final MessageManager messageManager;


    @PostMapping("/mail/sendCode/{mailAccount}")
    public Result<Boolean> sendCode(
            @Email(message = "请输入正确的邮箱类型")
            @PathVariable String mailAccount) {
        MailMessage message = MailMessage.buildRegisterMailMessage(mailAccount);
        //发送邮件
        Boolean flag = messageManager.sendMessage(message);
        return Result.success(flag);
    }


}
