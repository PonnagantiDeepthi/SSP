package ug.daes.onboarding.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
	
	private static Logger logger = LoggerFactory.getLogger(ExecutionTimeAspect.class);

	/** The Constant CLASS. */
	final static String CLASS = "ExecutionTimeAspect";

    @Around("execution(* ug.daes.onboarding..*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    	
    	String methodName = joinPoint.getSignature().toShortString();
    	
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Executing method: " + methodName + " Execution time: " + executionTime + "ms");
        System.out.println("result :: " + result );
        
        logger.info(CLASS + " Executing method And Executing time {},{}" , methodName , executionTime  +" ms");
        
        logger.info(CLASS + " Executing method response {} " , result);

        return result;
    }

}