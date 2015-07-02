package com.dereekb.gae.model.extension.links.deprecated.functions;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used for annotating fields in a links handler.
 * 
 * Classes that implement LinksMethod methods should not implement other functions since introspection time will
 * increase with each function.
 * 
 * Examples:
 * public void linkObjects(LinksPair<T> pair) {
 * 
 * @author dereekb
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD })
public @interface LinksMethod {

	String value();

	LinksAction action() default LinksAction.LINK;

}
