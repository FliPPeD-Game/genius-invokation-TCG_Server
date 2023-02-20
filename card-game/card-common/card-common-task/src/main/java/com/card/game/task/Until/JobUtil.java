package com.card.game.task.Until;

import com.card.game.task.job.base.BaseJob;

/**
 * 反射工具类
 *
 * @author cunzhiwang
 * @Date 2023/2/9 17:08
 */
public class JobUtil {
    /**
     * 根据全类名获取Job实例
     *
     * @param classname Job全类名
     * @return {@link BaseJob} 实例
     * @throws Exception 泛型获取异常
     */
    public static BaseJob getClass(String classname) throws Exception {
        Class<?> clazz = Class.forName(classname);
        return (BaseJob) clazz.newInstance();
    }

}
