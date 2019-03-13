package com.dereekb.gae.utilities.misc.range;

/**
 * Range with optional min/max/default value that returns a value within the range.
 * 
 * @author dereekb
 */
public class ComparableRange<T extends Comparable<T>> {

	private static final int GREATER_THAN = 1;
	private static final int LESS_THAN = -1;

	private T defaultValue;
	private T minValue;
	private T maxValue;

	public ComparableRange() {}

	public ComparableRange(T defaultValue) {
		this(defaultValue, null, null);
	}

	public ComparableRange(T defaultValue, T minValue, T maxValue) {
		super();
		this.defaultValue = defaultValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public final T getLimitedValue(T input) {

		T value = input;

		if (input == null) {
			value = defaultValue;
		}

		if (value != null) {

			// Must be greater or equal to the min.
			if (minValue != null) {
				int comparison = value.compareTo(minValue);

				if (comparison == LESS_THAN) {
					value = this.minValue;
				}
			}

			// Must be less than or equal to the max.
			if (maxValue != null) {
				int comparison = value.compareTo(maxValue);

				if (comparison == GREATER_THAN) {
					value = this.maxValue;
				}
			}

		}

		return value;
	}

	public T getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public T getMinValue() {
		return minValue;
	}

	public void setMinValue(T minValue) {
		this.minValue = minValue;
	}

	public T getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(T maxValue) {
		this.maxValue = maxValue;
	}

}
