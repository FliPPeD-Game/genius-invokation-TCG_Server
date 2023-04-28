package com.card.game.message;

import com.card.game.common.exception.BizException;
import com.card.game.common.result.ResultCode;
import com.card.game.message.enums.EmailBodyFormat;
import com.card.game.message.pojo.Message;
import com.card.game.message.pojo.mail.Attachment;
import com.card.game.message.pojo.mail.MailMessage;
import com.card.game.message.template.TemplateHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author tomyou
 * @version v1.0 2023-01-15-9:41 PM
 */
@Component
@RequiredArgsConstructor
public class MailMessageProvider implements MessageProvider {

    private final JavaMailSender javaMailSender;

    private final TemplateHandler templateHandler;

    @Override
    @SneakyThrows
    public Boolean sendMessage(Message message) {
        EmailBodyFormat emailBodyFormat = ((MailMessage) message).getEmailBodyFormat();
        List<Attachment> attachments = ((MailMessage) message).getAttachments();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        //接收者
        helper.setTo(message.getTo());
        //发送者
        helper.setFrom(message.getFrom());
        //标题
        helper.setSubject(((MailMessage) message).getSubject());
        //内容
        helper.setText(templateHandler.getTemplateContent(message), emailBodyFormat.equals(EmailBodyFormat.HTML));
        //附件不为空则添加附件
        if (!CollectionUtils.isEmpty(attachments)) {
            attachments.forEach(attachment -> {
                try {
                    helper.addAttachment(attachment.getName(), new ByteArrayResource(attachment.getContent()));
                } catch (MessagingException e) {
                    throw new BizException(ResultCode.SEND_MAIL_ERROR);
                }
            });
        }
        //发送邮件
        javaMailSender.send(mimeMessage);
        return Boolean.TRUE;
    }

    @Override
    public boolean supports(Class<?> message) {
        return MailMessage.class.isAssignableFrom(message);
    }
}
