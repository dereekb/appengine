package com.dereekb.gae.utilities.misc.keyed;

/**
 * {@link Keyed} extension that requires a key to be available always.
 * 
 * @author dereekb
 *
 * @param <K>
 *            key type
 */
public interface AlwaysKeyed<K>
        extends Keyed<K> {

	/**
	 * {@inheritDoc}
	 * 
	 * @return key value. Never {@code null}.
	 */
	@Override
	public K keyValue();

}
