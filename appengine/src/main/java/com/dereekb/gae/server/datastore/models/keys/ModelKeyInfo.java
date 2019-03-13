package com.dereekb.gae.server.datastore.models.keys;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface ModelKeyInfo {

	ModelKeyType value() default ModelKeyType.NUMBER;

	ModelKeyGenerationType generation() default ModelKeyGenerationType.AUTOMATIC;

}
