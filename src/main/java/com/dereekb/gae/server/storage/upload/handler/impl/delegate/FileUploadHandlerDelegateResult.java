package com.dereekb.gae.server.storage.upload.handler.impl.delegate;

import java.util.Collection;

/**
 * Result returned by a {@link FileUploadeHandlerDelegate}.
 *
 * @author dereekb
 *
 */
public interface FileUploadHandlerDelegateResult {

	/**
	 * Returns {@link UploadedFileResult} instances for files that were used.
	 *
	 * @return
	 */
	public Collection<UploadedFileResult> getUsedFiles();

	/**
	 * Returns {@link UploadedFileResult} instances for files that failed.
	 *
	 * @return
	 */
	public Collection<UploadedFileResult> getFailedFiles();

}
