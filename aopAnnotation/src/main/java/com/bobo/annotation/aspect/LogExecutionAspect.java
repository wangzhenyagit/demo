package com.bobo.annotation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author: wangzhenya create on 2020-01-28 15:49
 */
@Component
@Aspect
public class LogExecutionAspect {
    @Around("@annotation(com.bobo.annotation.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        final Object process = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.printf(proceedingJoinPoint.getSignature() + "execute time : " + (end - start) + " ms.");
        return process;
    }
}
