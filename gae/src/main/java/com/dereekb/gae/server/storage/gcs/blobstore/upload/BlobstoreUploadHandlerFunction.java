package com.dereekb.gae.server.storage.gcs.blobstore.upload;

import java.util.List;

/**
 * Function run by the BlobstoreUploadHandler that uses a collection of UploadedBlobFiles and returns a list of type <T>
 * 
 * @author dereekb
 * @param <T>
 */
public interface BlobstoreUploadHandlerFunction<T> {

	/**
	 * Uses the uploaded files to run some task, and returns result objects of type <T>.
	 * 
	 * The blobs that these files represent are deleted afterwards, so copies should be made if necessary.
	 * 
	 * @param files
	 * @return List of created objects, or an empty list if nothing was created.
	 */
	public List<T> runWithUploadedFiles(List<UploadedBlobFile> files);

}
