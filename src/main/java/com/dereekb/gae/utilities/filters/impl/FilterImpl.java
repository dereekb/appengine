package com.dereekb.gae.utilities.filters.impl;

import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;

/**
 * {@link AbstractFilter} implementation that returns a pre-defined
 * {@link FilterResult} for all input.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class FilterImpl<T> extends AbstractFilter<T> {

	private static final FilterImpl<Object> PASS = new FilterImpl<Object>(FilterResult.PASS);
	private static final FilterImpl<Object> FAIL = new FilterImpl<Object>(FilterResult.FAIL);
	
	private final FilterResult result;

	public FilterImpl(FilterResult result) throws IllegalArgumentException {
		if (result == null) {
			throw new IllegalArgumentException("Result cannot be null.");
		}

		this.result = result;
	}

	@SuppressWarnings("unchecked")
	public static <T> Filter<T> pass() {
		return (Filter<T>) PASS;
	}

	@SuppressWarnings("unchecked")
	public static <T> Filter<T> fail() {
		return (Filter<T>) FAIL;
	}

	public FilterResult getResult() {
		return this.result;
	}

	// MARK: Filter
	@Override
	public FilterResult filterObject(T object) {
		return this.result;
	}

	@Override
	public FilterResults<T> filterObjects(Iterable<? extends T> objects) {
		FilterResults<T> results = new FilterResults<T>();

		for (T object : objects) {
			results.add(this.result, object);
		}

		return results;
	}

	@Override
	public String toString() {
		return "FilterImpl [result=" + this.result + "]";
	}

}
