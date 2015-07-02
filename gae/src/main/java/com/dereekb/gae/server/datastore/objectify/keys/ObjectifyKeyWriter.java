package com.dereekb.gae.server.datastore.objectify.keys;

import java.util.List;

import com.googlecode.objectify.Key;


/**
 * Takes in object instances and converts them {@link Key} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link Key}'s generic type.
 * @param <E>
 *            Conversion type
 */
public interface ObjectifyKeyWriter<T, E> {

	public Key<T> writeKey(E element) throws IllegalKeyConversionException;

	public List<Key<T>> writeKeys(Iterable<E> elements) throws IllegalKeyConversionException;

}
