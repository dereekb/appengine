package com.dereekb.gae.utilities.misc.range;

public class IntegerRange extends ComparableRange<Integer> {

	public IntegerRange() {
		super();
	}

	public IntegerRange(Integer defaultValue) {
		super(defaultValue);
	}

	public IntegerRange(Integer defaultValue, Integer minValue, Integer maxValue) {
		super(defaultValue, minValue, maxValue);
	}

}
