package us.mapr.api.config;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Binding annotation for objects related to Google.
 */
@BindingAnnotation
@Retention(RUNTIME)
public @interface Google {
}
