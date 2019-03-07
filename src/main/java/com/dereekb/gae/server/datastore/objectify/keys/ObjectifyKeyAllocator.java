package com.dereekb.gae.server.datastore.objectify.keys;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;

/**
 * Used for allocating a range of Objectify {@link Key}s.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface ObjectifyKeyAllocator<T extends ObjectifyModel<T>> {

	/**
	 * Allocates an id.
	 *
	 * @return {@link Key}. Never {@code null}.
	 */
	public Key<T> allocateId();

	/**
	 * Allocates a number of ids.
	 *
	 * @param count Positive count.
	 * @return {@link KeyRange}. Never {@code null}.
	 */
	public List<Key<T>> allocateIds(int count);

}
