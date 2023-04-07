//package com.nccbc.digitalfreight.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @program: DigitalFreight
// * @description:
// * @author: Haochen Ren
// * @create: 2023-03-17 19:35
// **/
//@Aspect
//@Configuration
//@Slf4j
//public class WebLogAspect {
//
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    @Pointcut("execution(* com.gongj.mall.product.scenario.controller..*.*(..))")
//    public void webLog(){}
//
//    @Around(value = "webLog()")
//    public Object webLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        String className = joinPoint.getTarget().getClass().getName();
//        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//        String methodName = new StringBuffer(className.replaceFirst("com.gongj.mall.", ""))
//                .append(".")
//                .append(signature.getMethod().getName())
//                .append("():").toString();
//        log.info("==========> {} Request Params:{}",methodName, objectMapper.writeValueAsString(joinPoint.getArgs()));
//        Object proceed = joinPoint.proceed();
//        log.info("==========> {} Return Message:{}",methodName,objectMapper.writeValueAsString(proceed));
//        return proceed;
//    }
//}
