package com.maids.cc.LibraryManagementSystem.utils;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.maids.cc.LibraryManagementSystem.services.BookService.*(..)) || " +
            "execution(* com.maids.cc.LibraryManagementSystem.services.PatronService.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logMethodCall(JoinPoint joinPoint) {
        logger.info("Method called: " + joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: " + joinPoint.getSignature().getName(), exception);
    }
}
