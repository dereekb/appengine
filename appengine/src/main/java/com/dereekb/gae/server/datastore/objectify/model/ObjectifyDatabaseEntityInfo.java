package com.dereekb.gae.server.datastore.objectify.model;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityKeyEnforcement;

/**
 * Objectify entity information. Used by app generators.
 *
 * @author dereekb
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface ObjectifyDatabaseEntityInfo {

	ObjectifyDatabaseEntityKeyEnforcement keyEnforcement() default ObjectifyDatabaseEntityKeyEnforcement.DEFAULT;

}
