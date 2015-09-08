package com.dereekb.gae.server.storage.services.blobstore.object.blob.impl;

import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.services.blobstore.object.blob.BlobstoreKeyService;
import com.dereekb.gae.server.storage.services.gcs.object.file.GcsStorableFile;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFilename;

/**
 * Service for generating blob keys.
 *
 * @author dereekb
 */
public class BlobstoreKeyServiceImpl
        implements BlobstoreKeyService {

	private static final String GCS_BLOB_FORMAT = "/gs/%s/%s";

	private BlobstoreService blobstoreService;

	public BlobstoreKeyServiceImpl() {
		this(BlobstoreServiceFactory.getBlobstoreService());
	}

	public BlobstoreKeyServiceImpl(BlobstoreService blobstoreService) {
		super();
		this.blobstoreService = blobstoreService;
	}

	public BlobstoreService getBlobstoreService() {
		return this.blobstoreService;
	}

	public void setBlobstoreService(BlobstoreService blobstoreService) {
		this.blobstoreService = blobstoreService;
	}

	// MARK: BlobstoreKeyService
	@Override
	public BlobKey blobKeyForStorableFile(String gcsBucket,
	                                      StorableFile file) {
		String url = this.createGcsBlobUrl(file, gcsBucket);
		BlobKey blobKey = this.blobstoreService.createGsBlobKey(url);
		return blobKey;
	}

	@Override
	public BlobKey blobKeyForGcsStorableFile(GcsStorableFile file) {
		GcsFilename filename = file.getGcsFilename();
		String url = this.createGcsBlobUrl(filename);
		BlobKey blobKey = this.blobstoreService.createGsBlobKey(url);
		return blobKey;
	}

	// MARK: Internal
	private String createGcsBlobUrl(GcsFilename filename) {
		String gcsBucket = filename.getBucketName();
		String object = filename.getObjectName();
		String url = String.format(GCS_BLOB_FORMAT, gcsBucket, object);
		return url;
	}

	private String createGcsBlobUrl(StorableFile file,
	                                String gcsBucket) {
		String path = file.getFilePath();
		String url = String.format(GCS_BLOB_FORMAT, gcsBucket, path);
		return url;
	}

	@Override
	public String toString() {
		return "BlobstoreKeyServiceImpl [blobstoreService=" + this.blobstoreService + "]";
	}

}
