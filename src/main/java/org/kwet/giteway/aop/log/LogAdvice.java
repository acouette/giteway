package org.kwet.giteway.aop.log;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The Class LogAdvice : logs call to all the methods in giteway when debug mode is set.
 * It also provides the execution time of the method calls. 
 *
 * @author Antoine Couette
 */
@Aspect
@Component
public class LogAdvice {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(LogAdvice.class);

	/**
	 * Log around advice.
	 *
	 * @param pjp the joinpoint
	 * @return the object returned by the adviced method.
	 * @throws any Throwable thrown by the method call.
	 */
	@Around("execution(* org.kwet.giteway..*.*(..))")
	public Object logAroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

		if (log.isDebugEnabled()) {
			long start = new Date().getTime();
			StringBuilder methodDesc = new StringBuilder(pjp.getSignature().toShortString());
			log.debug("BEGIN : " + methodDesc.toString());
			Object retVal = pjp.proceed();
			long stop = new Date().getTime();
			log.debug("END : " + pjp.getSignature().toShortString() + " (Execution time : " + (stop - start)+" ms)");
			return retVal;
		} else {
			return pjp.proceed();
		}

	}
}
