package com.dereekb.gae.utilities.collections.set.dencoder.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.collections.set.dencoder.EncodedLongDecoder;
import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

/**
 * {@link EncodedLongDecoder} implementation
 *
 * @author dereekb
 *
 * @param <T>
 *            encoded type
 */
public class EncodedLongDecoderImpl<T>
        implements EncodedLongDecoder<T> {

	private Map<Integer, T> map;

	public EncodedLongDecoderImpl(Map<Integer, T> map) throws IllegalArgumentException {
		this.setMap(map);
	}

	public Map<Integer, T> getMap() {
		return this.map;
	}

	public void setMap(Map<Integer, T> map) throws IllegalArgumentException {
		if (map == null) {
			throw new IllegalArgumentException("Map cannot be null.");
		}

		this.map = map;
	}

	// MARK: Decoder
	@Override
	public Set<T> decode(Long encodedLong) {
		LongBitContainer container = new LongBitContainer(encodedLong);
		List<Byte> active = container.getAllActiveIndexes();
		return this.decode(active);
	}

	@Override
	public Set<T> decode(Iterable<? extends Number> encoded) {
		Set<T> decoded = new HashSet<>();

		for (Number e : encoded) {
			Integer index = e.intValue();
			T value = this.map.get(index);

			if (value != null) {
				decoded.add(value);
			}
		}

		return decoded;
	}

	@Override
	public String toString() {
		return "EncodedLongDecoderImpl [map=" + this.map + "]";
	}

}
