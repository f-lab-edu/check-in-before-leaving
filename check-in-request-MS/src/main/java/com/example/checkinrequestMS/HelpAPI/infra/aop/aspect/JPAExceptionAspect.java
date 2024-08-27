package com.example.checkinrequestMS.HelpAPI.infra.aop.aspect;


import com.example.checkinrequestMS.HelpAPI.infra.exceptions.jpa.JPAException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.example.checkinrequestMS.HelpAPI.infra.exceptions.jpa.JPAErrorCode.*;

@Component
@Aspect
public class JPAExceptionAspect {

    @Around("@annotation(JPARead)")
    public Object handleJPAReadException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new JPAException(ERROR_READ, e);

        }
    }

    @Around("@annotation(JPASave)")
    public Object handleJPASaveException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new JPAException(ERROR_SAVE, e);
        }
    }

    @Around("@annotation(JPAUpdate)")
    public Object handleJPAUpdateException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw new JPAException(ERROR_UPDATE, e);
        }
    }


}
