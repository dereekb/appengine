package com.dereekb.gae.server.auth.deprecated.permissions.evaluator;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Functions annotated with this will be called for handling a permission.
 * @author dereekb
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { METHOD })
public @interface PermissionEventHandlerFunction {
	
	String[] value();
	
}
