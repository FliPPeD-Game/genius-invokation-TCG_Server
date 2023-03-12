package com.card.game.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLogAspect {

    @Pointcut("execution(public * com.card.game.service.impl.*(..))")
    public void serviceImplLog() {
    }

    @Around("serviceImplLog()")
    public Object invoke(){
        return null;
    }
}
