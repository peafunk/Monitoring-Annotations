# Monitoring-Annotations
This component contains to two annotations that can be used in an application to record metrics in a Graphic DB.
Include this component in you application and configure the location of your StatsD host in the StatsDClient object of the monitoring-config.xml file and you can start recording metrics.
 
These are both method-level annotations:

The Annotations
@Timed - an around advice that times method execution.  Records the time in ms and posts into graphic DB

@Incremented - an after advice that will increase the count of the number of times the method has been executed


Sample Use
Incremented example:

@Incremented(notes = "appMetrics.myservice.mycontroller.dataaccessexception")
public @ResponseBody FormSubmitResponse handleException(DataAccessException ex) {
    return ERROR_VALIDATION_RESPONSE;
}
Timed Example:

@Timed(timingNotes ="appMetrics.myapp.myservice.doSomething")
public Object doSomething(){
   ...
}
