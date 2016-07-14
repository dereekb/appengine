package com.dereekb.gae.utilities.collections.set.dencoder;

import java.util.Set;

/**
 * Used as a converter that decodes a {@link String} with encoded values.
 *
 * @author dereekb
 *
 */
public interface SetDecoder<T, E> {

	public Set<T> decode(Iterable<? extends E> encoded);

}
