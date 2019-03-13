package com.dereekb.gae.utilities.collections.map;

import java.beans.ConstructorProperties;

import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;

/**
 * Pairing that allows inserting a single object with many keys.
 *
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            model type
 */
public class MapPairing<K, T> extends HandlerPair<Iterable<K>, T> {

	@ConstructorProperties({ "key", "object" })
	public MapPairing(K key, T object) {
		super(SingleItem.withValue(key), object);
	}

	@ConstructorProperties({ "keys", "object" })
	public MapPairing(Iterable<K> keys, T object) {
		super(keys, object);
	}

	public Iterable<K> getKeys() {
		return this.key;
	}

	public T getValue() {
		return this.object;
	}

}
