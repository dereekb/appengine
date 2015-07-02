package com.dereekb.gae.server.storage.gcs.blobstore.path;

import com.dereekb.gae.server.storage.file.StorableFile;
import com.dereekb.gae.server.storage.gcs.blobstore.BlobstoreKeyFactory;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * Implementation of the {@link BlobstorePathConverter} for retrieving elements
 * from the Google Cloud Storage.
 *
 * @author dereekb
 *
 */
public final class GcsBlobstorePathConverter
        implements BlobstorePathConverter {

	private final BlobstoreKeyFactory keyFactory;
	private final String bucket;

	public GcsBlobstorePathConverter(String bucket) {
		this(new BlobstoreKeyFactory(), bucket);
	}

	public GcsBlobstorePathConverter(BlobstoreKeyFactory keyFactory, String bucket) {
		this.keyFactory = keyFactory;
		this.bucket = bucket;
	}

	public BlobstoreKeyFactory getKeyFactory() {
		return this.keyFactory;
	}

	public String getBucket() {
		return this.bucket;
	}

	@Override
	public BlobKey blobKeyForFile(StorableFile file) {
		return this.keyFactory.blobKeyForStorageFile(this.bucket, file);
	}

}
