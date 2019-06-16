package com.dereekb.gae.server.storage.upload.reader.impl;

import java.io.IOException;

import com.dereekb.gae.server.deprecated.storage.exception.NoFileDataException;

/**
 * Delegate for {@link UploadedBlobFile}.
 * <p>
 * Used to retrieving data.
 * </p>
 *
 * @author dereekb
 *
 */
public interface UploadedBlobFileDelegate {

	/**
	 * Reads the {@code byte} array for the passed {@link UploadedBlobFile}.
	 *
	 * @param uploadedBlobFile
	 *            Uploaded file. Never {@code null}.
	 * @return {@code byte} array. Never {@code null}.
	 * @throws IOException
	 *             if an exception occurs while reading bytes.
	 * @throws NoFileDataException
	 *             if there is no data associated with this file.
	 */
	public byte[] readFileBytes(UploadedBlobFile uploadedBlobFile) throws IOException, NoFileDataException;

}
