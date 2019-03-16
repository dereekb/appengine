package com.dereekb.gae.server.datastore.objectify.core;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

/**
 * Used for read-only database operations.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyDatabaseEntityReader<T extends ObjectifyModel<T>> {

	// MARK: Get
	public T get(Key<T> key);

	public List<T> keysGet(Iterable<Key<T>> list);

	@Deprecated
	public List<T> refsGet(Iterable<Ref<T>> list);

	// MARK: Query
	public boolean exists(Key<T> key);

	public Query<T> makeQuery(boolean allowCache);

}
