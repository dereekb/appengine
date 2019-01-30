package com.thevisitcompany.gae.deprecated.model.storage.support;

import java.util.Collection;

import com.thevisitcompany.gae.deprecated.model.storage.StorageModel;
import com.thevisitcompany.gae.server.storage.file.StorageFile;
import com.thevisitcompany.gae.server.storage.functions.observers.DeleteFileObserverDelegate;
import com.thevisitcompany.gae.server.storage.path.StoragePathInfo;
import com.thevisitcompany.gae.utilities.collections.SingleItem;

/**
 * Default implementation of {@link DeleteFileObserverDelegate} for objects that extend {@link StorageModel}.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public class StorageModelDeleteFileObserverDelegate<T extends StorageModel<T>>
        implements DeleteFileObserverDelegate<T> {

	private StoragePathInfo<T> storagePathInfo;

	@Override
	public Collection<StorageFile> retrieveFilesToDelete(T object) {
		StorageFile file = storagePathInfo.getStorableItemFile(object, object);
		return SingleItem.withValue(file);
	}

	@Override
	public void handleMissingFile(StorageFile file,
	                              T object) {
		// Do nothing, since it means the files has already been deleted.
	}

	public StoragePathInfo<T> getStoragePathInfo() {
		return storagePathInfo;
	}

	public void setStoragePathInfo(StoragePathInfo<T> storagePathInfo) {
		this.storagePathInfo = storagePathInfo;
	}

}
