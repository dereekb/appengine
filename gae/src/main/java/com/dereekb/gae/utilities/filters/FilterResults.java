package com.dereekb.gae.utilities.filters;

import java.util.List;

import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

public class FilterResults<T> extends HashMapWithSet<FilterResult, T> {

	public FilterResults() {
		super();
	}

	public FilterResults(Iterable<T> sources) {
		this.addAll(FilterResult.PASS, sources);
    }
	
	public FilterResults(FilterResult result, Iterable<T> sources) {
		this.addAll(result, sources);
    }
	
	public void addResult(FilterResultWrapper<T> wrapper) {
		T object = wrapper.getObject();
		FilterResult result = wrapper.getResult();
		this.add(result, object);
	}
	
	public List<T> getPassingObjects() {
		return this.valuesForKey(FilterResult.PASS);
	}
	
	public List<T> getFailingObjects() {
		return this.valuesForKey(FilterResult.FAIL);
	}

	public void insert(FilterResults<T> results) {
		this.merge(results);
	}
	
}
