package com.peafunk.monitoring.service

/**
 * We need a service to implement an interface so that Spring AOP can use the Java Dynamic Proxy capabilities 
 */
public interface MonitorService {
	
    void doTiming()
	
    void doMoreTiming(String genericParam)    

    void doIncrement()
    
}
