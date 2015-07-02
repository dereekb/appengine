package com.dereekb.gae.utilities.collections.map;

import java.beans.ConstructorProperties;

import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.pairs.HandlerPair;

/**
 * Map pairing that allows inserting a single object with many keys.
 * 
 * @author dereekb
 * 
 * @param <T>
 * @param <U>
 */
public class MapPairing<T, U> extends HandlerPair<Iterable<T>, U> {

	@ConstructorProperties({ "key", "object" })
	public MapPairing(T key, U object) {
		super(SingleItem.withValue(key), object);
	}

	@ConstructorProperties({ "keys", "object" })
	public MapPairing(Iterable<T> keys, U object) {
		super(keys, object);
	}

	public Iterable<T> getKeys() {
		return this.key;
	}

	public U getValue() {
		return this.object;
	}

}
