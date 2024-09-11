package com.SlicK.api_gateway.Utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.SlicK.api_gateway..*(..))")
    public void logMethodArguments(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("Method: " + methodName + " | Arguments: " + Arrays.toString(args));
    }

    @AfterReturning(pointcut = "execution(* com.SlicK.api_gateway..*(..))", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method: " + methodName + " | Returned: " + result);
    }
}
