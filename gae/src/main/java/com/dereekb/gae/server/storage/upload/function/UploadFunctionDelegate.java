package com.dereekb.gae.server.storage.upload.function;

import com.dereekb.gae.server.storage.file.StorageFileInfo;

/**
 * Used by an UploadFunction instance for creating items from the stored item.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public interface UploadFunctionDelegate<T> {

	/**
	 * Creates a new object of type <T> for the uploaded file.
	 * 
	 * @param pair
	 */
	public T createForUpload(StorageFileInfo source);

}
