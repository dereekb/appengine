package com.dereekb.gae.utilities.collections.set;

import java.util.Set;

import com.dereekb.gae.utilities.collections.map.KeyDelegate;

/**
 * {@link DelegatedSet} with key information.
 * 
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            model type
 */
public interface KeyedDelegatedSet<K, T>
        extends DelegatedSet<T> {

	/**
	 * @return {@link KeyDelegate}. Never {@code null}.
	 */
	public KeyDelegate<K, T> getKeyDelegate();

	public Set<K> keySet();

}
