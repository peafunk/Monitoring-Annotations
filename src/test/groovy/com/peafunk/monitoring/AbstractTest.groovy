package com.peafunk.monitoring

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@ContextConfiguration(locations=["classpath*:com/peafunk/monitoring/monitoring-config.xml"])
@WebAppConfiguration
public class AbstractTest extends Specification {

}
