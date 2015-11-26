package com.webmd.monitoring.service

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

/**
 * Factory class for StatsdClient
 * @author pellington
 */
@Service
class StatsdClientFactory {

	StatsdClient statsdClient
	
	public StatsdClient getInstance(){
		return statsdClient
	}

	public StatsdClient getStatsdClient() {
		return statsdClient;
	}

	public void setStatsdClient(StatsdClient statsdClient) {
		this.statsdClient = statsdClient;
	}
	
	
}
