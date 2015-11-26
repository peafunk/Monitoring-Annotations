package com.webmd.monitoring

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@ContextConfiguration(locations=["classpath*:com/webmd/config/MedscapeAppConfig.xml", "classpath*:com/webmd/monitoring/monitoring-config.xml"])
@WebAppConfiguration
public class AbstractTest extends Specification {

}
