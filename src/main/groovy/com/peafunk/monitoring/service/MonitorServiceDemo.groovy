package com.peafunk.monitoring.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * This class is demo monitoring annotations.  It calls service impl with annotated methods
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class MonitorServiceDemo {

    @Autowired
    def MonitorService monitorService

    public void setMonitorServiceImpl(MonitorService monitorService) {
        this.monitorService = monitorService
    }

    public void demoMonitorTimingService() {
        this.monitorService.doTiming()
        this.monitorService.doMoreTiming("NON-STANDARD REQUEST")
		System.println('Done Timing methods')
    }
    
    public void demoMonitorIncrementService() {
        this.monitorService.doIncrement()
		System.println('Done Incrementing methods')
    }
}
