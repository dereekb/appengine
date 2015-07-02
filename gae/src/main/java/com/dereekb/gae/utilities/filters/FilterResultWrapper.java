package com.dereekb.gae.utilities.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Wraps an object with a filter result.
 * 
 * @author dereekb
 */
@SuppressWarnings("unused")
public class FilterResultWrapper<T> {

	private final T object;
	private FilterResult result;

	public FilterResultWrapper(T object) {
		this.object = object;
	}

	public T getObject() {
		return object;
	}

	public FilterResult getResult() {
		return result;
	}

	public void setResult(FilterResult result) {
		this.result = result;
	}

	private static <T> FilterResultWrapper<T> wrap(T object) {
		FilterResultWrapper<T> wrapper = new FilterResultWrapper<T>(object);
		return wrapper;
	}

	private static <T> List<FilterResultWrapper<T>> wrap(Collection<T> objects) {
		List<FilterResultWrapper<T>> wrappedObjects = new ArrayList<FilterResultWrapper<T>>();

		for (T object : objects) {
			FilterResultWrapper<T> wrappedObject = new FilterResultWrapper<T>(object);
			wrappedObjects.add(wrappedObject);
		}

		return wrappedObjects;
	}

}
