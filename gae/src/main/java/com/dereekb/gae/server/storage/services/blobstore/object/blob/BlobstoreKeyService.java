package com.dereekb.gae.server.storage.services.blobstore.object.blob;

import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.services.gcs.GcsStorableFile;
import com.google.appengine.api.blobstore.BlobKey;

/**
 *
 * @author dereekb
 *
 */
public interface BlobstoreKeyService {

	public BlobKey blobKeyForStorableFile(String gcsBucket,
	                                     StorableFile file);

	public BlobKey blobKeyForGcsStorableFile(GcsStorableFile file);

}
