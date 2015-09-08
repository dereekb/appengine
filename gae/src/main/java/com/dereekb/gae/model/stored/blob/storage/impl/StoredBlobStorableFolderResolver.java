package com.dereekb.gae.model.stored.blob.storage.impl;

import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobType;
import com.dereekb.gae.server.storage.object.folder.StorableFolder;
import com.dereekb.gae.server.storage.object.folder.StorableFolderResolver;
import com.dereekb.gae.utilities.collections.map.catchmap.CatchMap;


public class StoredBlobStorableFolderResolver
        implements StorableFolderResolver<StoredBlob> {

	private CatchMap<StoredBlobType, String> folderPaths;

	@Override
    public StorableFolder folderForObject(StoredBlob object) {


	    return null;
    }

}
