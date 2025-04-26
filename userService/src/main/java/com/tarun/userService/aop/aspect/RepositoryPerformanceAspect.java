package com.tarun.userService.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryPerformanceAspect {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryPerformanceAspect.class);

    @Value("${logging.slow.query.threshold:500}")
    private long slowQueryThreshold; // in ms

    @Around("within(com.tarun.userService.repository..*)")
    public Object measureRepoTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long duration = System.currentTimeMillis() - start;
        if (duration > slowQueryThreshold) {
            logger.warn("Slow query: {}#{} took {}ms",
                    pjp.getSignature().getDeclaringTypeName(),
                    pjp.getSignature().getName(),
                    duration);
        }
        return result;
    }
}