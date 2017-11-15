package com.dereekb.gae.utilities.collections.set.dencoder;

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
public interface EncodedLongDencoder<T>
        extends EncodedLongDecoder<T>, SetEncoder<T, Integer> {

	public Long encodeLong(Iterable<T> values);

}
