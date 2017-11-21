package com.dereekb.gae.utilities.collections.set;

import java.util.Collection;
import java.util.Map;

import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;

/**
 * Special set that takes in models that contain unique set keys, but aren't
 * keys themselves.
 * <p>
 * Is generally backed by a {@link Map}.
 * 
 * @author dereekb
 *
 */
public interface DelegatedSet<T> {

	public Collection<T> values();

	public int size();

	public boolean isEmpty();

	public boolean contains(T value) throws NoModelKeyException;

	public T add(T value) throws NoModelKeyException;

	public T remove(T value) throws NoModelKeyException;

	public void addAll(Iterable<T> values) throws NoModelKeyException;

	public void removeAll(Iterable<T> values) throws NoModelKeyException;

	public void clear();

}
