package com.dereekb.gae.utilities.collections.set;

import java.util.Set;

/**
 * Used as a converter that decodes a
 *
 * @author dereekb
 *
 */
public interface SetDecoder<T> {

	public Set<T> decode(String encodedRoles);

	public Set<T> decode(Iterable<String> encodedRoles);

}
