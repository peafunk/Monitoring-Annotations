package com.peafunk.monitoring.service

import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.Log
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

import com.peafunk.monitoring.annotations.Incremented
import com.peafunk.monitoring.annotations.Timed


/**
 * This service demonstrates the usage of the monitoring aspects
 */
@Service("monitorServiceBean")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class MonitorServiceImpl implements MonitorService {
	
	private static Log logger = LogFactory.getLog(MonitorServiceImpl.class);

    /**
     * Timing this method execution
     */
	@Override
    @Timed(timingNotes = "test.monitoring.slowservice")
    public void doTiming() {
        try { 
			//Sleep for a short time to simulate doing something
            Thread.sleep(100)
        } catch (InterruptedException e) {
            logger.error("Error sleeping: " + e)
        }
        logger.info("Timed a method execution")
    }

    /**
     * Timing method with a param 
     */
	@Override
    @Timed(timingNotes = "test.monitoring.fastservice")
    public void doMoreTiming(String genericParam) {
        logger.info("Timed another method execution")
    }

    /**
     * Increment a method use count 
     */
    @Incremented(notes = "test.monitoring.incrementing")
	@Override
	public void doIncrement() {
        logger.info("Incremented the count on doIncrement()")
	}
    
}
