package com.dereekb.gae.server.storage.object.file.impl;

import com.dereekb.gae.server.deprecated.storage.exception.StorableObjectGenerationException;
import com.dereekb.gae.server.deprecated.storage.object.file.Storable;
import com.dereekb.gae.server.deprecated.storage.object.file.StorableFile;
import com.dereekb.gae.server.deprecated.storage.object.file.StorableFilePathResolver;
import com.dereekb.gae.server.deprecated.storage.object.folder.StorableFolder;

/**
 * {@link StorableFilePathResolver} that uses a static {@link StorableFolder}.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link Storable} object type
 */
public class StaticStorableFilePathResolverImpl<T extends Storable>
        implements StorableFilePathResolver<T> {

	private StorableFolder folder;

	public StaticStorableFilePathResolverImpl(StorableFolder folder) {
		this.folder = folder;
	}

	public StorableFolder getFolder() {
		return this.folder;
	}

	public void setFolder(StorableFolder folder) {
		this.folder = folder;
	}

	// MARK: StorableFilePathResolver
	@Override
	public StorableFile resolveStorageFile(T object) throws StorableObjectGenerationException {
		StorableFileImpl file = new StorableFileImpl(object, this.folder);
		return file;
	}

	@Override
	public String toString() {
		return "StaticStorableFilePathResolverImpl [folder=" + this.folder + "]";
	}

}
