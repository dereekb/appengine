package com.dereekb.gae.utilities.misc.bit;

/**
 * Utility for calculating byte counts.
 *
 * @author dereekb
 *
 */
public class ByteSizeUtility {

	public static final Long BYTES_IN_KILOBYTE = 1000L;
	public static final Long BYTES_IN_MEGABYTE = 1000 * BYTES_IN_KILOBYTE;

	public static Long kiloBytes(Integer kiloBytes) {
		return BYTES_IN_KILOBYTE * kiloBytes;
	}

	public static Long megaBytes(Integer megaBytes) {
		return BYTES_IN_MEGABYTE * megaBytes;
	}

}
