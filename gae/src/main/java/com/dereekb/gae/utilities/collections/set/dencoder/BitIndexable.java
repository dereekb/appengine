package com.dereekb.gae.utilities.collections.set.dencoder;

/**
 * Object that has a indexable/hashable value.
 *
 * @author dereekb
 *
 * @deprecated Use {@link IndexCoded} instead.
 */
@Deprecated
public interface BitIndexable {

	/**
	 * Byte index.
	 * 
	 * @return {@code byte}. Never {@code null}.
	 */
	public byte getIndex();

}
