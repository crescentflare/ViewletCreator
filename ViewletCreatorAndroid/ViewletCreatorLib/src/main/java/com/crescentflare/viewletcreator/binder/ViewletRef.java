package com.crescentflare.viewletcreator.binder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Viewlet creator binder: annotation
 * Annotate fields to automatically assign with the ViewletAnnotationBinder
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ViewletRef
{
    String value() default "";
}
