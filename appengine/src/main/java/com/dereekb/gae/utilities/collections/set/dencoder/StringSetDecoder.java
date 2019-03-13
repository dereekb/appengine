package com.dereekb.gae.utilities.collections.set.dencoder;

import java.util.Set;

/**
 * {@link SetDecoder}
 *
 * @author dereekb
 *
 * @param <T>
 *            decoded type
 */
public interface StringSetDecoder<T>
        extends SetDecoder<T, String> {

	public Set<T> decode(String encoded);

}
