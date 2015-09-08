package com.dereekb.gae.server.storage.services.blobstore.object.path.impl;

import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.services.blobstore.object.blob.impl.BlobstoreKeyServiceImpl;
import com.dereekb.gae.server.storage.services.blobstore.object.path.BlobKeyResolver;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * Implementation of the {@link BlobKeyResolver} for retrieving elements
 * from the Google Cloud Storage.
 *
 * @author dereekb
 *
 */
public class BlobKeyResolverImpl
        implements BlobKeyResolver {

	private String gcsBucket;
	private BlobstoreKeyServiceImpl keyFactory;

	public BlobKeyResolverImpl(String gcsBucket) {
		this(new BlobstoreKeyServiceImpl(), gcsBucket);
	}

	public BlobKeyResolverImpl(BlobstoreKeyServiceImpl keyFactory, String gcsBucket) {
		this.keyFactory = keyFactory;
		this.gcsBucket = gcsBucket;
	}

	public BlobstoreKeyServiceImpl getKeyFactory() {
		return this.keyFactory;
	}

	public void setKeyFactory(BlobstoreKeyServiceImpl keyFactory) {
		this.keyFactory = keyFactory;
	}

	public String getGcsBucket() {
		return this.gcsBucket;
	}

	public void setGcsBucket(String gcsBucket) {
		this.gcsBucket = gcsBucket;
	}

	// MARK: BlobKeyResolver
	@Override
	public BlobKey blobKeyForFile(StorableFile file) {
		return this.keyFactory.blobKeyForStorableFile(this.gcsBucket, file);
	}

	@Override
	public String toString() {
		return "BlobKeyResolverImpl [keyFactory=" + this.keyFactory + ", gcsBucket=" + this.gcsBucket + "]";
	}

}
