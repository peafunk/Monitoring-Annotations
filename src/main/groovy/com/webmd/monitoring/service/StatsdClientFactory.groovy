package com.webmd.monitoring.service

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

@Service
/**
 * Facotry class to get instance of StatsD client
 * @author pellington
 *
 */
class StatsdClientFactory {

	@Autowired
	StatsdClient statsdClient
	
	private StatsdClient getInstance(){
		return statsdClient
	}
	
}
