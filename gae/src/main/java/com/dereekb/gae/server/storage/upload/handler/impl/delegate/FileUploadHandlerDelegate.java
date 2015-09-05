package com.dereekb.gae.server.storage.upload.handler.impl.delegate;

import com.dereekb.gae.server.storage.upload.handler.FileUploadHandlerResult;
import com.dereekb.gae.server.storage.upload.handler.impl.FileUploadHandlerImpl;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileSet;

/**
 * Delegate for {@link FileUploadHandlerImpl}.
 *
 * @author dereekb
 *
 */
public interface FileUploadHandlerDelegate {

	/**
	 * Uses the uploaded files.
	 *
	 * @param set
	 *            {@link UploadedFileSet}. Never {@code null}.
	 * @return {@link FileUploadHandlerResult}. Never {@code null}.
	 * @throws RuntimeException
	 *             if any exceptions occur.
	 */
	public FileUploadHandlerDelegateResult handleUploadedFiles(UploadedFileSet set) throws RuntimeException;

}
