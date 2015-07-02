package com.thevisitcompany.gae.deprecated.model.mod.data.conversion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Named function that converts a model to its data transfer object clone.
 *
 * @author dereekb
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ModelConversionFunction {

	public boolean isDefault() default false;

	public String[] value();

}
