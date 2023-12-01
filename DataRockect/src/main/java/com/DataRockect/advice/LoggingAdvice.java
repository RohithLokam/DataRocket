package com.DataRockect.advice;
//package com.DataRocket.advice;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
////@Aspect
////@Component
////public class LoggingAdvice {
////	
////	org.slf4j.Logger log=LoggerFactory.getLogger(LoggingAdvice.class);
////	
////	@Pointcut(value="execution(* com.DataRocket.*.*.*(..))")
////	public void myPointcut() {
////		
////	}
////	
////	@Around("myPointcut()")
////	public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
////		ObjectMapper mapper=new ObjectMapper();
////		
////		String methodName=pjp.getSignature().getName();
////		String className=pjp.getTarget().getClass().toString();
////		Object[] array=pjp.getArgs();
////		log.info("method invoked "+className+" : "+methodName+"()"+" arguments  : "+mapper.writeValueAsString(array));
////		Object object=pjp.proceed();
////		log.info(className+" : "+methodName+"()"+" Response  : "+mapper.writeValueAsString(object));
////
////		return object;
////	}
////
////}
//
//
//
//import org.slf4j.Logger;
//;
//
//@Aspect
//@Component
//public class LoggingAdvice {
//
//    private final Logger log = LoggerFactory.getLogger(LoggingAdvice.class);
//
//    @Pointcut("execution(* com.DataRocket.*.*.*(..))")
//    public void serviceMethods() {
//    }
//
//    @Around("serviceMethods()")
//    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.debug("Entering method: {}", joinPoint.getSignature().getName());
//        Object result = joinPoint.proceed();
//        log.debug("Exiting method: {}", joinPoint.getSignature().getName());
//        return result;
//    }
//}
