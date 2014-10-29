package com.webmd.monitoring

import com.webmd.monitoring.AbstractTest
import com.webmd.monitoring.service.MonitorServiceDemo
import org.springframework.beans.factory.annotation.Autowired

import spock.lang.Ignore;

/**
 * User: pellington
 * Date: 2/3/14
 * Time: 10:39 AM
 */
class IncrementTest extends AbstractTest{
	
	@Autowired
	MonitorServiceDemo monitorServiceDemo

	def"Get article meta data test"(){

		when:
			monitorServiceDemo.demoMonitorIncrementService()

		then:
			1 == 1

	}
	
}
