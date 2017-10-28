package com.dereekb.gae.model.extension.objectify.annotation;

import com.googlecode.objectify.condition.ValueIf;

/**
 * {@link ValueIf} implementation that checks that value is more than one.
 *
 * @author dereekb
 *
 */
public class IfGreaterThanOne extends ValueIf<Number> {

	@Override
	public boolean matchesValue(Number value) {
		return value != null && value.doubleValue() > 1;
	}

}
