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

		if (log.isDebugEnabled()) {
			log.debug("BEGIN : " + pjp.getSignature().toShortString() + (pjp.getArgs() != null ? " " + pjp.getArgs() : ""));
		} else if (log.isInfoEnabled()) {
			log.info("BEGIN : " + pjp.getSignature().toString());
		}
		Object retVal = pjp.proceed();
		if (log.isDebugEnabled()) {
			log.debug("END : " + pjp.getSignature().toString());
		} else if (log.isInfoEnabled()) {
			log.info("END : " + pjp.getSignature().toString());
		}
		return retVal;
	}
}
