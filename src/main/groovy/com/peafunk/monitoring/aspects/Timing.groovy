package com.webmd.monitoring.aspects

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired

import com.webmd.monitoring.service.StatsdClient
import com.webmd.monitoring.annotations.Timed

import java.lang.reflect.Method

/**
 * This aspect times the execution of a method (join point) and records this in StatsD *
 * @author pellington
 */
@Aspect
public class Timing {

	@Autowired
	private StatsdClient statsdClient
	private static Log logger = LogFactory.getLog(Timing.class)
	
    /**
     * This around advice adds timing to any method annotated with the Timed annotation.
     * It binds the annotation to the parameter timedAnnotation so that the values are available at runtime.
     * Also note that the retention policy of the annotation needs to be RUNTIME.
     *
     * @param pjp             - the join point for this advice
     * @param timedAnnotation - the Timed annotation as declared on the method
     * @return
     * @throws Throwable
     */
    @Around("@annotation(timedAnnotation)")
    public Object processSystemRequest(final ProceedingJoinPoint pjp, Timed timedAnnotation) throws Throwable {
        try {
            def start = System.currentTimeMillis() as long
            def retVal = pjp.proceed() as Object
            def end = System.currentTimeMillis() as long
            def differenceMs = end - start as long
			
			MethodSignature methodSignature = (MethodSignature) pjp.signature
			Method targetMethod = methodSignature.method
			
            //get the value of timing notes as declared in the method annotation
            def timingNotes = timedAnnotation.timingNotes()

            logger.info("Timing joinpoint  " + targetMethod.declaringClass.name + "." + targetMethod.name + " took " + differenceMs + " ms : timing notes: " + timingNotes)

            def graphNode
            if (timedAnnotation.timingNotes().length()==0) {
            	graphNode = targetMethod.declaringClass.name + "." + targetMethod.name
            } else {
            	graphNode = timingNotes
            }
			
            statsdClient.timing(graphNode, (int) differenceMs);            
            return retVal;
			
        } catch (Throwable t) {
            System.out.println("Error occured timing method execution: " + t.getMessage())
            throw t
        }

    }

}
