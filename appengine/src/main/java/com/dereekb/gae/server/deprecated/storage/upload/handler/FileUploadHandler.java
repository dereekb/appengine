package com.dereekb.gae.server.storage.upload.handler;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.deprecated.storage.upload.exception.FileUploadFailedException;

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
	 * @param type
	 *            Upload type.
	 * @param request
	 *            {@link HttpServletRequest} request. Never {@code null}.
	 * @return {@link FileUploadeHandlerResult}. Never {@code null}.
	 * @throws FileUploadFailedException
	 *             Thrown if the upload fails. All uploaded files are deleted
	 *             before this exception is thrown.
	 */
	public FileUploadHandlerResult handleUploadRequest(String type,
	                                                   HttpServletRequest request) throws FileUploadFailedException;

}
