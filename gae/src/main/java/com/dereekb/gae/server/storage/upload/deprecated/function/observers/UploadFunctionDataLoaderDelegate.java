package com.dereekb.gae.server.storage.upload.deprecated.function.observers;

import com.dereekb.gae.server.storage.upload.deprecated.UploadedFile;

/**
 * Interface for loading the bytes of an uploaded file.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface UploadFunctionDataLoaderDelegate<U extends UploadedFile> {

	/**
	 * Attempts to read the bytes of the given file.
	 *
	 * @param uploadedFile
	 * @return Bytes array of the file's data, or null if the file could not be read.
	 */
	public byte[] readBytes(U file);

}
