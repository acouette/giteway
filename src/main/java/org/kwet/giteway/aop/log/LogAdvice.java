package org.kwet.giteway.aop.log;

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
			StringBuilder methodDesc = new StringBuilder(pjp.getSignature().toShortString());
			if(log.isDebugEnabled()){
				methodDesc.append(" args : [");
				for (Object obj : pjp.getArgs()) {
					methodDesc.append(" ").append(obj.toString());
				}
				methodDesc.append(" ]");
			}
			log.debug("BEGIN : " + methodDesc.toString());
		}

		Object retVal = pjp.proceed();
		
		if(log.isDebugEnabled()){
			log.debug("END : " + pjp.getSignature().toShortString());
		}
		
		return retVal;
	}
}
