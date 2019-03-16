package com.dereekb.gae.model.extension.objectify.annotation;

import com.googlecode.objectify.condition.ValueIf;

/**
 * {@link ValueIf} implementation that checks whether or not the value is 1.
 *
 * @author dereekb
 *
 */
public class IfOne extends ValueIf<Number> {

	@Override
	public boolean matchesValue(Number value) {
		return value != null && value.doubleValue() == 1;
	}

}
