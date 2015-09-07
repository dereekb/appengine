package com.dereekb.gae.server.storage.object.folder.impl;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.storage.object.folder.FolderPathResolver;
import com.dereekb.gae.server.storage.object.folder.StorableFolder;

/**
 * Default implementation of {@link FolderPathResolver} for usage with
 * {@link UniqueModel} instances.
 * <p>
 * Folder paths generated are a combination of the {@link ModelKey} on the
 * model, and the format specified.
 * </p>
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public final class ModelFolderPathResolver<T extends UniqueModel>
        implements FolderPathResolver<T> {

	private String format;

	public ModelFolderPathResolver(String format) throws IllegalArgumentException {
		this.setFormat(format);
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) throws IllegalArgumentException {
		if (format == null || format.isEmpty()) {
			throw new IllegalArgumentException("Path format cannot be null or empty.");
		}

		this.format = format;
	}

	// MARK: FolderPathResolver
	@Override
	public StorableFolder folderForObject(T object) {
		ModelKey key = object.getModelKey();
		String keyString = key.keyAsString();
		String folderPath = String.format(this.format, keyString);
		StorableFolderImpl folder = new StorableFolderImpl(folderPath);
		return folder;
	}

	@Override
	public String toString() {
		return "ModelFolderPathResolver [format=" + this.format + "]";
	}

}
