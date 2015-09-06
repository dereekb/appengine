package com.dereekb.gae.server.storage.upload.deprecated.function;

import com.dereekb.gae.server.storage.file.StorableFileInfo;

/**
 * Used by an UploadFunction instance for creating items from the stored item.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public interface UploadFunctionDelegate<T> {

	/**
	 * Creates a new object of type <T> for the uploaded file.
	 *
	 * @param pair
	 */
	public T createForUpload(StorableFileInfo source);

}
