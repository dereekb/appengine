package com.dereekb.gae.server.storage.gcs.blobstore.path;

import com.dereekb.gae.server.storage.file.StorableFile;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * Used for retrieving a {@link StorableFile} to a {@link BlobKey}.
 *
 * @author dereekb
 */
public interface BlobstorePathConverter {

	public BlobKey blobKeyForFile(StorableFile file);

}
