//package com.card.game.aop;
//
//
//import cn.hutool.json.JSONUtil;
//import cn.hutool.log.LogFactory;
// import lombok.extern.slf4j.Slf4j;
//import lombok.val;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
//@Aspect
//@Component
//@Slf4j
//public class ServiceLogAspect {
//
//    @Pointcut("execution(public * com.card.game.service.*.*(..))")
//    public void serviceImplLog() {
//    }
//
//    @Around("serviceImplLog()")
//    public Object invoke(ProceedingJoinPoint point) throws Throwable {
//        val realAopResult = getRealAopResult(point);
//        val realClass = getRealClass(point);
//        if (realAopResult != null && realAopResult.print()) {
//            val log = LogFactory.get(realClass);
//            val objects = JSONUtil.parseArray(point.getArgs());
//            log.info("method is:{},args is:{}", point.getSignature().getName(), objects.toJSONString(2));
//        }
//        val proceed = point.proceed();
//        return proceed;
//    }
//
//    private AopResult getRealAopResult(ProceedingJoinPoint proceedingJoinPoint) {
//        String method = proceedingJoinPoint.getSignature().getName();
//        //AopResult aopResult = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(AopResult.class);
//        AopResult aopResult = null;
//        Method proxyMethod = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
//        try {
//            Method realMethod = proceedingJoinPoint.getTarget().getClass().getDeclaredMethod(method, proxyMethod.getParameterTypes());
//            aopResult = realMethod.getAnnotation(AopResult.class);
//        } catch (Exception e) {
//            //log.error("failed to get the realMethod, the error is :", e);
//            aopResult = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(AopResult.class);
//        }
//        return aopResult;
//    }
//
//    private Class getRealClass(ProceedingJoinPoint proceedingJoinPoint) {
//        try {
//            return proceedingJoinPoint.getTarget().getClass();
//        } catch (Exception e) {
//            //log.error("failed to get the realMethod, the error is :", e);
//            return null;
//        }
//    }
//}
