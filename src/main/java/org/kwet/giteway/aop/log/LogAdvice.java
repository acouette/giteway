package org.kwet.giteway.aop.log;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvice {

	private static Logger log = LoggerFactory.getLogger(LogAdvice.class);

	@Around("execution(* org.kwet.giteway..*.*(..))")
	public Object logAroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

		
		
		if(log.isDebugEnabled()){
			long start = new Date().getTime();
			StringBuilder methodDesc = new StringBuilder(pjp.getSignature().toShortString());
			log.debug("BEGIN : " + methodDesc.toString());
			Object retVal = pjp.proceed();
			long stop = new Date().getTime();
			log.debug("END : " + pjp.getSignature().toShortString()+" - time : "+(stop-start));
			return retVal;
		}else{
			return pjp.proceed();
		}
		
		
	}
}
