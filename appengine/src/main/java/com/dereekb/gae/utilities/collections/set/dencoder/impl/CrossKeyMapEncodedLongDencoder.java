package com.dereekb.gae.utilities.collections.set.dencoder.impl;

import java.util.Map;

import com.dereekb.gae.utilities.collections.map.CrossKeyMap;
import com.dereekb.gae.utilities.collections.map.impl.CrossKeyMapImpl;

/**
 * Uses a {@link CrossKeyMap} to encode and decode values.
 * 
 * @author dereekb
 *
 * @param <T>
 *            key type
 */
public class CrossKeyMapEncodedLongDencoder<T> extends AbstractEncodedLongDencoderImpl<T> {

	private CrossKeyMap<Integer, T> crossKeyMap;

	public CrossKeyMapEncodedLongDencoder(Map<Integer, T> map) throws IllegalArgumentException {
		super(map);
	}

	@Override
	public void setMap(Map<Integer, T> map) throws IllegalArgumentException {
		super.setMap(map);
		this.crossKeyMap = CrossKeyMapImpl.makeY(map);
	}

	// MARK: AbstractEncodedLongDencoderImpl
	@Override
	protected int getIndexForValue(T value) {
		return this.crossKeyMap.getX(value);
	}

	@Override
	public String toString() {
		return "CrossKeyMapEncodedLongDencoder [crossKeyMap=" + this.crossKeyMap + "]";
	}

}
