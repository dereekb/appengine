package com.dereekb.gae.utilities.misc.keyed;

/**
 * Denotes that the model is keyed in some way.
 * 
 * @author dereekb
 *
 * @param <K>
 *            key type
 */
public interface Keyed<K> {

	/**
	 * Returns the key value.
	 * 
	 * @return key value. {@link null} if no key.
	 */
	public K keyValue();

}
