package com.dereekb.gae.utilities.collections.set.dencoder.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.collections.set.dencoder.BitIndexable;
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
public class EncodedLongDencoderImpl<T extends BitIndexable> extends EncodedLongDecoderImpl<T>
        implements EncodedLongDencoder<T> {

	public EncodedLongDencoderImpl(Map<Integer, T> map) throws IllegalArgumentException {
		super(map);
	}

	// MARK: EncodedLongDecoder
	@Override
	public Long encodeLong(Iterable<T> values) {
		LongBitContainer container = new LongBitContainer();
		Set<Byte> bytes = this.encode(values);

		for (Byte b : bytes) {
			container.setBit(true, b);
		}

		return container.getValue();
	}

	// MARK: Encoder
	@Override
	public Set<Byte> encode(Iterable<? extends T> values) {
		Set<Byte> bytes = new HashSet<Byte>();

		for (T value : values) {
			byte index = value.getIndex();
			bytes.add(index);
		}

		return bytes;
	}

	@Override
	public String toString() {
		return "EncodedLongDencoderImpl [map=" + this.getMap() + "]";
	}

}
