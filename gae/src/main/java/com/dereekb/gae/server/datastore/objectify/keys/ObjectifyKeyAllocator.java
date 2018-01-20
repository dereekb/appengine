package com.dereekb.gae.server.datastore.objectify.keys;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.KeyRange;

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
	public KeyRange<T> allocateIds(int count);

}
