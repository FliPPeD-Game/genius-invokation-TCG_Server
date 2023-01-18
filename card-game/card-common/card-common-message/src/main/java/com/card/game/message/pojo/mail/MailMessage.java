package com.card.game.message.pojo.mail;

import com.card.game.message.constants.MessageConstant;
import com.card.game.message.enums.EmailBodyFormat;
import com.card.game.message.enums.TemplateEnum;
import com.card.game.message.pojo.AbstractMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @author tomyou
 * @version v1.0 2023-01-15-9:27 PM
 */
@Getter
@Setter
public class MailMessage extends AbstractMessage {

    /**
     * 标题
     */
    private String subject;

    /**
     * 类型(默认是HTML类型)
     */
    private EmailBodyFormat emailBodyFormat = EmailBodyFormat.HTML;

    /**
     * 附件
     */
    private List<Attachment> attachments;


    public static MailMessage buildRegisterMailMessage(String mailAccount) {
        MailMessage message = new MailMessage();
        //主题
        message.setSubject(MessageConstant.REGISTER_MAIL_SUBJECT);
        //接收者
        message.setTo(mailAccount);
        //发送者
        message.setFrom(MessageConstant.DEFAULT_SENDER);
        //模板类型
        message.setTemplateEnum(TemplateEnum.REGISTER_TEMPLATE);
        return message;
    }
}
