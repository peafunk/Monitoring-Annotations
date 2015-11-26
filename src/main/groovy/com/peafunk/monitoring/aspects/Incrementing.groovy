package com.peafunk.monitoring.aspects

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired

import com.peafunk.monitoring.service.StatsdClient
import com.peafunk.monitoring.annotations.Incremented

import java.lang.reflect.Method

/**
 * This aspect times the execution of a method (join point)
 * @author pellington
 */
@Aspect
public class Incrementing {

	@Autowired
	def StatsdClient statsdClient;
	def Log logger = LogFactory.getLog(Timing.class)
	
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
    @After("@annotation(incrementedAnnotation)")
    public Object processSystemRequest(final JoinPoint pjp, Incremented incrementedAnnotation) throws Throwable {
        try {
            def start = System.currentTimeMillis() as long
            def retVal = pjp.proceed() as Object
            def end = System.currentTimeMillis() as long
            def differenceMs = end - start as long

            MethodSignature methodSignature = (MethodSignature) pjp.signature
            Method targetMethod = methodSignature.method
            
            //get the value of timing notes as declared in the method annotation
            def note = incrementedAnnotation.notes()

            logger.debug("Incrementing joinpoint  " + targetMethod.declaringClass.name + "." + targetMethod.name + " took " + differenceMs + " ms : notes: " + note)

        	//If no note is passed, use the declaring method
            def graphNode
            if (note.length()==0) {
            	graphNode = targetMethod.declaringClass.name + "." + targetMethod.name
            } else {
            	graphNode = note
            }
          
    		//Log increment
            statsdClient.increment(graphNode, 1)
            
            return retVal
			
        } catch (Throwable t) {
            System.out.println("Error occured incrementing method count: " + t.getMessage())
            throw t
        }

    }

}
