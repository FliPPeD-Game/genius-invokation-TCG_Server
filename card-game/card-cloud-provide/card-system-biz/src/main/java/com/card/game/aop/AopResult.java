package com.card.game.aop;

import java.lang.annotation.*;

/**
 * 是否记录AOP切面的返回值
 * Created by pearzhou on 2018/8/31.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface AopResult {
    boolean print() default true;

    boolean printCallEnd() default true;
}
