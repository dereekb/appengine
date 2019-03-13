package com.dereekb.gae.server.storage.upload.reader;

import java.io.IOException;

import com.dereekb.gae.server.storage.exception.NoFileDataException;

/**
 * Represents an uploaded file.
 *
 * @author dereekb
 *
 */
public interface UploadedFile {

	/**
	 * @return {@code byte} array of file data. Never {@code null}.
	 * @throws IOException
	 *             if the bytes cannot be read.
	 * @throws NoFileDataException
	 *             if no data is available.
	 */
	public byte[] getUploadedFileBytes() throws IOException, NoFileDataException;

	/**
	 * @return {@link UploadedFileInfo} for this file. Never null.
	 */
	public UploadedFileInfo getUploadedFileInfo();

}
