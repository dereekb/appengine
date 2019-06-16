package com.dereekb.gae.server.storage.upload;

import com.dereekb.gae.server.deprecated.storage.upload.exception.FileUploadUrlCreationException;
import com.dereekb.gae.web.api.model.extension.upload.exception.InvalidUploadTypeException;

/**
 * Used for initializing and creating a url to upload to.
 *
 * @author dereekb
 *
 */
public interface FileUploadUrlFactory {

	/**
	 * Creates a new URL for uploading to.
	 *
	 * @param data
	 *            {@link String} value containing optional data related to
	 *            creating an upload.
	 * @return {@link String} containing a valid URL to upload to. Never
	 *         {@code null}.
	 * @throws FileUploadUrlCreationException
	 *             when the upload url cannot be created.
	 * @throws InvalidUploadTypeException
	 *             when the upload type is unavailable.
	 */
	public String makeUploadUrl(String data) throws FileUploadUrlCreationException, InvalidUploadTypeException;

}
