package com.dereekb.gae.server.storage.upload;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.storage.upload.exception.FileUploadFailedException;

/**
 * Used to handle a {@link HttpServletRequest} containing an uploaded file.
 *
 * @author dereekb
 *
 */
public interface FileUploadHandler {

	/**
	 * Handles an upload request.
	 *
	 * @param request
	 *            {@link HttpServletRequest} request. Never {@code null}.
	 * @return {@link FileUploadeHandlerResult}. Never {@code null}.
	 * @throws FileUploadFailedException
	 *             if the upload fails.
	 */
	public FileUploadHandlerResult handleUploadRequest(HttpServletRequest request) throws FileUploadFailedException;

}
