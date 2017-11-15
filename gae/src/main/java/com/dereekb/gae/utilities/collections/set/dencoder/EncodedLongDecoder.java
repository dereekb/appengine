package com.dereekb.gae.utilities.collections.set.dencoder;

import java.util.Set;

/**
 * {@link SetDecoder} for a {@link Long} to the decoded type.
 *
 * @author dereekb
 *
 * @param <T>
 *            decoded type
 */
public interface EncodedLongDecoder<T>
        extends SetDecoder<T, Number> {

	/**
	 * Decodes the input encoded long into a
	 * {@link Set} of values.
	 * 
	 * @param encodedRoles
	 *            {@link Long}. Never {@code null}.
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<T> decode(Long encodedLong);

}
