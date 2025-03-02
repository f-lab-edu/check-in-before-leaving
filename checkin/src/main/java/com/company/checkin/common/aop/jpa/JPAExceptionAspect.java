package com.company.checkin.common.aop.jpa;


import com.company.checkin.help.infra.exceptions.db.jpa.JPAException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.company.checkin.help.infra.exceptions.db.jpa.JPAErrorCode.*;

@Component
@Aspect
public class JPAExceptionAspect {

    @Around("@annotation(com.company.checkin.common.aop.jpa.JPARead)")
    public Object handleJPAReadException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new JPAException(ERROR_READ, e);

        }
    }

    @Around("@annotation(com.company.checkin.common.aop.jpa.JPASave)")
    public Object handleJPASaveException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new JPAException(ERROR_SAVE, e);
        }
    }

    @Around("@annotation(com.company.checkin.common.aop.jpa.JPAUpdate)")
    public Object handleJPAUpdateException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new JPAException(ERROR_UPDATE, e);
        }
    }


}
