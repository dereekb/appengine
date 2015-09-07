package com.dereekb.gae.server.storage.services.blobstore.object.path;

import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * Used for converting a {@link StorableFile} to a {@link BlobKey}.
 *
 * @author dereekb
 */
public interface BlobstorePathConverter {

	public BlobKey blobKeyForFile(StorableFile file);

}
