# Monitoring-Annotations
This component contains to two annotations that can be used in an application to record metrics in StatsD enabled UI tool.
Include this component in you application and configure the location of your StatsD instance in the StatsDClient object of the monitoring-config.xml file and you can start recording metrics.
 
These are both method-level annotations:

==========================
== The Annotations
==========================
@Timed - an around advice that will time how long a method takes to execute.  Records the time in ms and posts into graphical UI tool (currently Graphite)
@Incremented - an after advice that will increase the count of the number of times the method has been executed


==========================
== Sample Use 
==========================
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
