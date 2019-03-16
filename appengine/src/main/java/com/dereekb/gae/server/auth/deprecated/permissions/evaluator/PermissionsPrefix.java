package com.dereekb.gae.server.auth.deprecated.permissions.evaluator;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Prefix to be annotated to a class that will cause the PermissionsEvaluator to append the defined value when consulted.
 * 
 * For example, if the permission is "edit", and the targetObject is "place", the evaluator will evaluate "place.edit".
 * @author dereekb
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { TYPE })
public @interface PermissionsPrefix {

	public String value();
	
}
