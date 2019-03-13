package com.dereekb.gae.utilities.data;

import com.dereekb.gae.server.storage.exception.InvalidFileDataException;

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
	 * @throws InvalidFileDataException
	 */
	public byte[] modifyBytesContent(byte[] bytes) throws InvalidFileDataException;

}
