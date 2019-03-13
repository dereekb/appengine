package com.dereekb.gae.server.storage.upload.handler.impl.delegate;

import com.dereekb.gae.server.storage.upload.reader.UploadedFile;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

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
	public UploadedFile getUploadedFile();

	/**
	 * Returns data about the created type, if applicable.
	 *
	 * @return {@link ApiResponseData} describing the object created.
	 *         {@code null} if nothing was created.
	 */
	public ApiResponseData getResponseData();

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
