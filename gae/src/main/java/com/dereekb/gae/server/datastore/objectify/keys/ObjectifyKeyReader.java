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

	/**
	 * Converts the input key.
	 *
	 * @param key
	 *            input key.
	 * @return Converted object. Never {@code null}.
	 * @throws IllegalKeyConversionException
	 *             if the input key fails being converted.
	 * @throws NullPointerException
	 *             if the input key is {@code null}.
	 */
	public E readKey(Key<T> key) throws IllegalKeyConversionException, NullPointerException;

	/**
	 * Converts the input keys.
	 *
	 * @param keys
	 *            key values. Never {@code null}.
	 * @return {@link List} of converted objects.
	 * @throws IllegalKeyConversionException
	 */
	public List<E> readKeys(Iterable<? extends Key<T>> keys) throws IllegalKeyConversionException;

}
