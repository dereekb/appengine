package com.dereekb.gae.server.storage.folder;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Default implementation of {@link FolderPathResolver} for usage with
 * {@link UniqueModel} instances.
 *
 * Folder paths generated are a combination of the {@link ModelKey} on the
 * model, and the format specified.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class ModelFolderPathResolver<T extends UniqueModel>
        implements FolderPathResolver<T> {

	private final String format;

	public ModelFolderPathResolver(String format) {
		this.format = format;
	}

	@Override
	public StorableFolder folderForObject(T object) {
		ModelKey key = object.getModelKey();
		String keyString = key.keyAsString();
		String folderPath = String.format(this.format, keyString);
		StorageFolder folder = new StorageFolder(folderPath);
		return folder;
	}

	@Override
	public String toString() {
		return "ModelFolderPathResolver [format=" + this.format + "]";
	}

}
