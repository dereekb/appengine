package com.dereekb.gae.server.storage.upload.handler.impl.delegate;

import com.dereekb.gae.server.storage.upload.reader.UploadedFile;

/**
 * Wraps an {@link UploadedFile} and describes how the file was used.
 *
 * @author dereekb
 *
 */
public interface UploadedFileResult {

	/**
	 * Returns the original uploaded file.
	 *
	 * @return {@link UploadedFile}. Never {@code null}.
	 */
	public UploadedFile getFile();

	/**
	 * Returns the created type, if applicable.
	 *
	 * @return {@link String} of the type. {@code null} if nothing was created.
	 */
	public String getCreatedType();

	/**
	 * Returns the created type, if applicable.
	 *
	 * @return {@link Object} created. {@code null} if nothing was created.
	 */
	public Object getCreatedObject();

	/**
	 * Whether or not the {@link UploadedFile} was used.
	 *
	 * @return {@code true} if the file was used.
	 */
	public boolean fileWasUsed();

	/**
	 * Returns a message about the file, if applicable.
	 *
	 * @return {@link String} message concerning the uploaded file.
	 */
	public String getFileMessage();

}
