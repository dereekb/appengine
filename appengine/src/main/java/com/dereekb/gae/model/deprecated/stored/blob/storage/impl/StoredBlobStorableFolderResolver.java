package com.dereekb.gae.model.stored.blob.storage.impl;

import com.dereekb.gae.model.deprecated.stored.blob.StoredBlob;
import com.dereekb.gae.model.deprecated.stored.blob.StoredBlobType;
import com.dereekb.gae.server.storage.object.folder.StorableFolder;
import com.dereekb.gae.server.storage.object.folder.StorableFolderResolver;
import com.dereekb.gae.utilities.collections.map.catchmap.CatchMap;

/**
 * {@link StorableFolderResolver} implementation.
 *
 * @author dereekb
 *
 */
public class StoredBlobStorableFolderResolver
        implements StorableFolderResolver<StoredBlob> {

	private CatchMap<StoredBlobType, StorableFolder> folders;

	public StoredBlobStorableFolderResolver(CatchMap<StoredBlobType, StorableFolder> folders) {
		this.folders = folders;
	}

	public CatchMap<StoredBlobType, StorableFolder> getFolders() {
		return this.folders;
	}

    public void setFolders(CatchMap<StoredBlobType, StorableFolder> folders) {
		this.folders = folders;
	}

	// MARK: StorableFolderResolver
	@Override
    public StorableFolder folderForObject(StoredBlob object) {
		StoredBlobType type = object.getBlobType();
		StorableFolder folder = this.folders.get(type);
		return folder;
	}

	@Override
	public String toString() {
		return "StoredBlobStorableFolderResolver [folders=" + this.folders + "]";
    }

}
