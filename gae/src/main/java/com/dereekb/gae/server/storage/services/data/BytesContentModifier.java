package com.dereekb.gae.server.storage.services.data;

import com.dereekb.gae.server.storage.exception.InvalidFileDataException;

/**
 * Used to modify {@code byte[]} data.
 *
 * @author dereekb
 *
 */
public interface BytesContentModifier {

	/**
	 * Modifies the input {@code byte[]} data.
	 *
	 * @param bytes
	 * @return
	 * @throws InvalidFileDataException
	 */
	public byte[] modifyBytesContent(byte[] bytes) throws InvalidFileDataException;

}
