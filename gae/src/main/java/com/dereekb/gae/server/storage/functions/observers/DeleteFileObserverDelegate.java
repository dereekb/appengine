package com.dereekb.gae.server.storage.functions.observers;

import java.util.Collection;

import com.dereekb.gae.server.storage.file.StorageFile;

/**
 * Observer used by {@link DeleteFileObserver}.
 *
 * @author dereekb
 */
@Deprecated
public interface DeleteFileObserverDelegate<T> {

	/**
	 * @return Returns a list of {@link StorageFile}s of the items that should be deleted from the input object.
	 */
	public Collection<StorageFile> retrieveFilesToDelete(T object);

	/**
	 * Optional function that is called when a file is missing.
	 *
	 * @param file File that is missing.
	 * @param object Object that contains/references the missing file.
	 */
	public void handleMissingFile(StorageFile file,
	                              T object);

}
