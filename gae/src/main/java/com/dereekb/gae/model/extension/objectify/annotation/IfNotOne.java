package com.dereekb.gae.model.extension.objectify.annotation;

import com.googlecode.objectify.condition.ValueIf;

/**
 * {@link ValueIf} implementation that checks that value is not 1.
 *
 * @author dereekb
 *
 */
public class IfNotOne extends ValueIf<Number> {

	@Override
	public boolean matchesValue(Number value) {
		return value != null && value.doubleValue() != 1;
	}

}
