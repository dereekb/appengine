package com.dereekb.gae.utilities.collections.set.dencoder.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.collections.set.dencoder.EncodedLongDencoder;
import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

/**
 * {@link EncodedLongDencoder} implementation
 *
 * @author dereekb
 *
 * @param <T>
 *            decoded type
 */
public abstract class AbstractEncodedLongDencoderImpl<T> extends EncodedLongDecoderImpl<T>
        implements EncodedLongDencoder<T> {

	public AbstractEncodedLongDencoderImpl(Map<Integer, T> map) throws IllegalArgumentException {
		super(map);
	}

	// MARK: EncodedLongDecoder
	@Override
	public Long encodeLong(Iterable<T> values) {
		LongBitContainer container = new LongBitContainer();
		Set<Integer> bytes = this.encode(values);

		for (Integer b : bytes) {
			container.setBit(true, b);
		}

		return container.getValue();
	}

	// MARK: Encoder
	@Override
	public Set<Integer> encode(Iterable<? extends T> values) throws IllegalArgumentException {
		Set<Integer> indexes = new HashSet<Integer>();

		for (T value : values) {
			int index = this.getIndexForValue(value);
			indexes.add(index);
		}

		return indexes;
	}

	// MARK: Internal
	protected abstract int getIndexForValue(T value) throws IllegalArgumentException;

	@Override
	public String toString() {
		return "EncodedLongDencoderImpl [map=" + this.getMap() + "]";
	}

}
