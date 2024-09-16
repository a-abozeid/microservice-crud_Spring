package com.SlicK.basic_crud.Utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.SlicK.basic_crud..*(..))")
    public void logMethodArguments(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("Method: " + methodName + " | Arguments: " + Arrays.toString(args));
    }

    @AfterThrowing(pointcut = "execution(* com.SlicK.basic_crud..*(..))", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method: " + methodName + " | Exception: " + exception.getMessage());
    }

    @AfterReturning(pointcut = "execution(* com.SlicK.basic_crud..*(..))", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method: " + methodName + " | Returned: " + result);
    }

    @Around("execution(* com.SlicK.basic_crud..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method: " + methodName + " | Execution time: " + (endTime - startTime) + " ms");
        return result;
    }

}
