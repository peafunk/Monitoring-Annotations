package com.webmd.monitoring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark the methods in order to time their execution
 * It is critical that the retention policy be RUNTIME so that the annotation values are available at runtime to the advice
 *
 * @author pellington
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Timed {

    //sample value passed into the advice of methods marked with this annotation
    String timingNotes();
}
