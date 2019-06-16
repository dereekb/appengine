package com.dereekb.gae.utilities.data;

import com.dereekb.gae.utilities.data.exception.InvalidBytesException;

/**
 * Used to modify {@code byte[]} data.
 *
 * @author dereekb
 *
 */
public interface BytesModifier {

	/**
	 * Modifies the input {@code byte[]} data.
	 *
	 * @param bytes
	 * @return
	 * @throws InvalidBytesException
	 */
	public byte[] modifyBytesContent(byte[] bytes) throws InvalidBytesException;

}
