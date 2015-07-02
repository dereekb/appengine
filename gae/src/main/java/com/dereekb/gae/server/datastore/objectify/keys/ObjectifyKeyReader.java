package com.dereekb.gae.server.datastore.objectify.keys;

import java.util.List;

import com.googlecode.objectify.Key;

/**
 * Takes in {@link Key} instances and converts them to other types.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link Key}'s generic type.
 * @param <E>
 *            Conversion type
 */
public interface ObjectifyKeyReader<T, E> {

	public E readKey(Key<T> key) throws IllegalKeyConversionException;

	public List<E> readKeys(Iterable<Key<T>> keys) throws IllegalKeyConversionException;

}
