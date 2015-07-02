package com.dereekb.gae.model.extension.objectify.annotation;

import java.lang.reflect.Field;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.condition.InitializeIf;
import com.googlecode.objectify.condition.ValueIf;

/**
 * Object that checks whether or not the value is equal to a newly initialized version of that class.
 *
 * Requires that an object have a no-args constructor and an overridden implementation of isEqual().
 *
 * @author dereekb
 *
 */
public class IfNew extends ValueIf<Object>
        implements InitializeIf {

	private Object defaultValue;

	@Override
	public void init(ObjectifyFactory fact,
	                 Field field) {

		Class<?> type = field.getType();
		this.defaultValue = fact.construct(type);
	}

	@Override
	public boolean matchesValue(Object value) {
		return this.defaultValue.equals(value);
	}

}
