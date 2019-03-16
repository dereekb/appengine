package com.dereekb.gae.utilities.collections.set.dencoder;

import java.util.Set;

import com.dereekb.gae.utilities.collections.set.dencoder.exception.SetEncoderException;

/**
 * Used for encoding values from a set.
 *
 * @author dereekb
 *
 * @param <T>
 *            decoded type
 * @param <E>
 *            encoded type
 * @see SetDecoder
 */
public interface SetEncoder<T, E> {

	/**
	 * Encodes the input into a {@link Set}.
	 * 
	 * @param encoded
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link Set}. Never {@code null}.
	 * @throws SetEncoderException
	 *             if one or more encoded values cannot be represented.
	 */
	public Set<E> encode(Iterable<? extends T> encoded) throws SetEncoderException;

}
