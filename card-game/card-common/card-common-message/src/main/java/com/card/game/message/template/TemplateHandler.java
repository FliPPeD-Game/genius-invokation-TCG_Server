package com.card.game.message.template;

import cn.hutool.core.util.RandomUtil;
import com.card.game.common.exception.BizException;
import com.card.game.common.redis.RedisCache;
import com.card.game.common.redis.constants.RedisPrefixConstant;
import com.card.game.common.result.ResultCode;
import com.card.game.message.enums.TemplateEnum;
import com.card.game.message.pojo.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author tomyou
 * @version v1.0 2023-01-08-8:29 PM
 */
@Component
@RequiredArgsConstructor
public class TemplateHandler {
    private final TemplateEngine templateEngine;

    private final RedisCache redisCache;


    public String getTemplateContent(Message message) {
        Context context = new Context();
        TemplateEnum typeEnum = message.getTemplateEnum();
        switch (typeEnum) {
            case REGISTER_TEMPLATE:
                setRegisteredTemplateContext(context, message);
                break;

            default:
                break;
        }

        return templateEngine.process(typeEnum.getValue(), context);
    }

    /**
     * 设置发送邮件注册码模板
     *
     * @param context context
     */
    public void setRegisteredTemplateContext(Context context, Message message) {
        //随机生成6位验证码
        String verityCode = RandomUtil.randomString(6).toUpperCase();
        //缓存的key
        String cacheKey = RedisPrefixConstant.MAIL_CODE_PREFIX + message.getTo();
        //设置context
        context.setVariable("code", verityCode);
        //如果缓存中已经存在了则抛错,防止用户重复发送
        if (!StringUtils.isEmpty(redisCache.getCacheObject(cacheKey))) {
            throw new BizException(ResultCode.MAIL_CODE_IS_SEND);
        }
        //放入到缓存中,有效期5分钟
        redisCache.setCacheObject(cacheKey, verityCode, 5, TimeUnit.MINUTES);
    }
}
