package com.dereekb.gae.utilities.collections.set.dencoder;

import java.util.Set;

/**
 * Used for encoding values from a set.
 *
 * @author dereekb
 *
 * @param <T>
 *            decoded type
 * @param <E>
 *            encoded type
 * @see {@link SetDecoder}
 */
public interface SetEncoder<T, E> {

	public Set<E> encode(Iterable<? extends T> encoded);

}
