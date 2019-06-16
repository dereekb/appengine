package com.dereekb.gae.server.storage.object.file.impl;

import com.dereekb.gae.server.deprecated.storage.exception.StorableObjectGenerationException;
import com.dereekb.gae.server.deprecated.storage.object.file.Storable;
import com.dereekb.gae.server.deprecated.storage.object.file.StorableFile;
import com.dereekb.gae.server.deprecated.storage.object.file.StorableFilePathResolver;
import com.dereekb.gae.server.deprecated.storage.object.folder.StorableFolder;
import com.dereekb.gae.server.deprecated.storage.object.folder.StorableFolderResolver;

/**
 * {@link StorableFilePathResolver} implementation that uses a
 * {@link StorableFolderResolver} to build relative paths for {@link Storable}
 * objects.
 *
 * @author dereekb
 *
 */
public class StorableFilePathResolverImpl<T extends Storable>
        implements StorableFilePathResolver<T> {

	private StorableFolderResolver<T> folderResolver;

	public StorableFilePathResolverImpl(StorableFolderResolver<T> folderResolver) {
		this.folderResolver = folderResolver;
	}

	public StorableFolderResolver<T> getFolderResolver() {
		return this.folderResolver;
	}

	public void setFolderResolver(StorableFolderResolver<T> folderResolver) {
		this.folderResolver = folderResolver;
	}

	// MARK: StorableFilePathResolver
	@Override
	public StorableFile resolveStorageFile(T object) throws StorableObjectGenerationException {
		StorableFolder folder = this.folderResolver.folderForObject(object);
		StorableFileImpl file = new StorableFileImpl(object, folder);
		return file;
	}

	@Override
	public String toString() {
		return "StorableFilePathResolverImpl [folderResolver=" + this.folderResolver + "]";
	}

}
