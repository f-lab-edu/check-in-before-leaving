package com.company.checkin.common.aop.testtime;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


@Aspect
@Slf4j
public class TestTimeAspect {

    @Around("@annotation(com.company.checkin.common.aop.testtime.TestTime)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLUE = "\u001B[34m";

        long startTime = System.nanoTime();
        log.info("Method execution started at: " + startTime + ANSI_BLUE);

        Object proceed = joinPoint.proceed();

        long endTime = System.nanoTime();
        log.info("Method execution ended at: " + endTime + ANSI_RESET);
        log.info("Total execution time in nanoseconds: " + (endTime - startTime) + ANSI_BLUE);
        log.info("Total execution time in milliseconds: " + (endTime - startTime) / 1000000 + ANSI_BLUE);

        return proceed;
    }


}
