package com.dereekb.gae.utilities.misc.bit;

/**
 * Utility class.
 *
 * @author dereekb
 *
 */
public final class BitIndex {

	public static final Integer BITS_IN_HEX_BYTE = 4;
	public static final Integer BITS_IN_BYTE = 8;

	public static Integer hexIndex(int hexIndex) {
		return hexIndex * BITS_IN_HEX_BYTE;
	}

	public static Integer byteIndex(int byteIndex) {
		return byteIndex * BITS_IN_BYTE;
	}

}
