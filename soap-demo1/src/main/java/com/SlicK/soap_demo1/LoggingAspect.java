package com.SlicK.soap_demo1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.SlicK.soap_demo1..*(..))")
    public void logMethodArguments(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("Method: " + methodName + " | Arguments: " + Arrays.toString(args));
    }

    @AfterThrowing(pointcut = "execution(* com.SlicK.soap_demo1..*(..))", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method: " + methodName + " | Exception: " + exception.getMessage());
    }

    @AfterReturning(pointcut = "execution(* com.SlicK.soap_demo1..*(..))", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method: " + methodName + " | Returned: " + result);
    }

    @Around("execution(* com.SlicK.soap_demo1..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // Proceed with the actual method call
        long endTime = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method: " + methodName + " | Execution time: " + (endTime - startTime) + " ms");

        return result;
    }
}
