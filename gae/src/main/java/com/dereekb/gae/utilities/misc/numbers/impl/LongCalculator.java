package com.dereekb.gae.utilities.misc.numbers.impl;

import com.dereekb.gae.utilities.misc.numbers.Calculator;

/**
 * {@link Calculator} implementation for {@link Long}.
 * 
 * @author dereekb
 *
 */
public class LongCalculator
        implements Calculator<Long> {

	public static final LongCalculator SINGLETON = new LongCalculator();

	// MARK: Calculator
	@Override
	public Long zero() {
		return 0L;
	}

	@Override
	public Long add(Long a,
	                Long b) {
		return a + b;
	}

	@Override
	public Long subtract(Long a,
	                     Long b) {
		return a - b;
	}

	@Override
	public String toString() {
		return "LongCalculator []";
	}

}
