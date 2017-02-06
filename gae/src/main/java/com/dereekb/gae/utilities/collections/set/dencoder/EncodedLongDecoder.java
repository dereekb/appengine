package com.dereekb.gae.utilities.collections.set.dencoder;

import java.util.Set;

/**
 *
 * @author dereekb
 *
 * @param <T>
 *            decoded type
 */
public interface EncodedLongDecoder<T>
        extends SetDecoder<T, Number> {

	public Set<T> decode(Long encodedLong);

}
