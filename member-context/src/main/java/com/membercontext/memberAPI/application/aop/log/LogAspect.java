package com.membercontext.memberAPI.application.aop.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;


@Aspect
public class LogAspect {

    @Around("@annotation(com.membercontext.memberAPI.application.aop.log.Log)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLUE = "\u001B[34m";

        long startTime = System.nanoTime();
        System.out.println(ANSI_BLUE + "Method execution started at: " + startTime + ANSI_RESET);

        Object proceed = joinPoint.proceed();

        long endTime = System.nanoTime();
        System.out.println(ANSI_BLUE + "Method execution ended at: " + endTime + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Total execution time in nanoseconds: " + (endTime - startTime) + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Total execution time in milliseconds: " + (endTime - startTime) / 1000000 + ANSI_RESET);

        return proceed;
    }


}
