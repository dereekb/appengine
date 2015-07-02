package com.dereekb.gae.server.storage.gcs.blobstore.upload.file;

import com.dereekb.gae.server.storage.gcs.blobstore.upload.UploadedBlobFile;

public interface DelegatedBlobstoreUploadDelegate<T> {

	/**
	 * Checks to see if the file should be loaded or not.
	 * 
	 * @param file
	 * @return True if the file should be loaded or not.
	 */
	public boolean shouldLoadFile(UploadedBlobFile file);

	/**
	 * Filters the target file.
	 * 
	 * @param file
	 * @return True if the file is usable.
	 */
	public boolean isValidFile(UploadedBlobFile file);

	/**
	 * Uses the target file.
	 * 
	 * @param file
	 * @return Should always return an object of type <T>.
	 */
	public T useFile(UploadedBlobFile file);

}
