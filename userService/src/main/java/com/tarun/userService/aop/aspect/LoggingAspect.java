package com.tarun.userService.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // pointcut for all methods in your service package (and subpackages)
    @Pointcut("execution(* com.tarun.userService.service..*(..))")
    public void serviceLayer() {}

    @Before("serviceLayer()")
    public void logMethodEntry(JoinPoint jp) {
        logger.info("Entering {}.{} with arguments = {}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                jp.getArgs());
    }

    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logMethodExit(JoinPoint jp, Object result) {
        logger.info("Exiting {}.{} returning = {}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                result);
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logMethodException(JoinPoint jp, Throwable ex) {
        logger.error("Exception in {}.{}: {}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                ex.getMessage(), ex);
    }
}
