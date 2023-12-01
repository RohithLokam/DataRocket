package com.DataRockect.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class Logging {
    org.slf4j.Logger logger = LoggerFactory.getLogger(Logging.class);

    @Pointcut("execution(* com.DataRockect.*.*.*(..))")
    private String serviceMethods() {
    	return "hiii";
    }

    @After("serviceMethods()")
    public void logMethodEntry(JoinPoint jp){
    	String methodName=jp.getSignature().getName();
    	Class<? extends Object> className=jp.getTarget().getClass();
        logger.debug("Entered to "+className+" "+methodName);
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logMethodExit(Object result) {
        logger.debug("Exiting method with result: {}", result);
    }
}
