package com.dereekb.gae.server.storage.upload.impl;

import com.dereekb.gae.server.storage.upload.FileUploadHandlerResult;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileSet;

/**
 * Delegate for {@link FileUploadHandlerImpl}.
 *
 * @author dereekb
 *
 */
public interface FileUploadHandlerImplDelegate {

	/**
	 * Uses the uploaded files.
	 *
	 * @param set
	 *            {@link UploadedFileSet}. Never {@code null}.
	 * @return {@link FileUploadHandlerResult}. Never {@code null}.
	 * @throws RuntimeException
	 *             if any exceptions occur.
	 */
	public FileUploadHandlerResult handleUploadedFiles(UploadedFileSet set) throws RuntimeException;

}
