package com.dereekb.gae.server.datastore.objectify.query;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.SimpleQuery;

public class ObjectifySimpleQueryFilter<T> implements ObjectifyKeyRequestFilter<T> {

	private static String CONDITION_IN = "in";
	
	Iterable<Key<T>> keys;
	
	public ObjectifySimpleQueryFilter(Iterable<Key<T>> keys){
		this.keys = keys;
	}

	@Override
	public SimpleQuery<T> filter(SimpleQuery<T> query) {
		return query.filterKey(CONDITION_IN, keys);
	}
}
