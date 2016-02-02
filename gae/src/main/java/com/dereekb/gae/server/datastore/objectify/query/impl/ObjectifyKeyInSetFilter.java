package com.dereekb.gae.server.datastore.objectify.query.impl;

import java.util.Collections;
import java.util.Set;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifySimpleQueryFilter;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * {@link ObjectifySimpleQueryFilter} that checks that the results are in the
 * specified keys.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyKeyInSetFilter<T> implements ObjectifySimpleQueryFilter<T> {

	private static final String CONDITION_IN = "in";

	private Set<Key<T>> keys;

	public ObjectifyKeyInSetFilter(Set<Key<T>> keys) {
		this.setKeys(keys);
	}

	public Set<Key<T>> getKeys() {
		return this.keys;
	}

	public void setKeys(Set<Key<T>> keys) {
		if (keys == null) {
			keys = Collections.emptySet();
		}

		this.keys = keys;
	}

	// MARK: ObjectifySimpleQueryFilter
	@Override
	public SimpleQuery<T> filter(SimpleQuery<T> query) {
		return query.filterKey(CONDITION_IN, this.keys);
	}

	@Override
	public String toString() {
		return "ObjectifyKeyInSetFilter [keys=" + this.keys + "]";
	}

}
