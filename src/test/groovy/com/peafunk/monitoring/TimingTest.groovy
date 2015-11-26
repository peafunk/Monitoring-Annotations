package com.peafunk.monitoring

import com.peafunk.monitoring.AbstractTest
import com.peafunk.monitoring.service.MonitorServiceDemo
import org.springframework.beans.factory.annotation.Autowired

import spock.lang.Ignore;

/**
 * User: pellington
 * Date: 2/3/14
 * Time: 10:39 AM
 */
class TimingTest extends AbstractTest{
	
	@Autowired
	MonitorServiceDemo monitorServiceDemo

	def"Timing test"(){

		when:
			monitorServiceDemo.demoMonitorTimingService()

		then:
			1 == 1

	}
	
}
