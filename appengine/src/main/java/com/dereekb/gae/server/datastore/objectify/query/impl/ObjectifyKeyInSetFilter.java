package com.dereekb.gae.server.datastore.objectify.query.impl;

import java.util.Collection;
import java.util.HashSet;
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

	public ObjectifyKeyInSetFilter(Collection<Key<T>> keys) {
		this.setKeys(keys);
	}

	public Set<Key<T>> getKeys() {
		return this.keys;
	}

	public void setKeys(Collection<Key<T>> keys) {
		this.keys = new HashSet<Key<T>>();

		if (keys != null) {
			this.keys.addAll(keys);
		}
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
