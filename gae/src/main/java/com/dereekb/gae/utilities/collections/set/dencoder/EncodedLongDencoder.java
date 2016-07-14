package com.dereekb.gae.utilities.collections.set.dencoder;

import java.util.Set;

import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

/**
 * Used for decoding an encoded {@link Long} value, generally built using a
 * {@link LongBitContainer}
 *
 * @author dereekb
 *
 * @param <T>
 *            decoded type
 */
public interface EncodedLongDencoder<T extends BitIndexable>
        extends EncodedLongDecoder<T>, SetEncoder<T, Byte> {

	public Set<T> decode(Long encodedLong);

	public Long encodeLong(Iterable<T> values);

}
