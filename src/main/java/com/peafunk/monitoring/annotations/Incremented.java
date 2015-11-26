package com.peafunk.monitoring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark methods as having been executed. It simply counts the number of executions
 *
 * @author pellington
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Incremented {

    //sample value passed into the advice of methods marked with this annotation
    String notes();
}
