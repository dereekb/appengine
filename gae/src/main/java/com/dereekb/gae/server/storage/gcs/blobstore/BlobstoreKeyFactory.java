package com.dereekb.gae.server.storage.gcs.blobstore;

import com.dereekb.gae.server.storage.file.StorableFile;
import com.dereekb.gae.server.storage.gcs.GcsStorageFileRequest;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFilename;

/**
 * Factory for generating blob keys.
 *
 * @author dereekb
 */
public final class BlobstoreKeyFactory {

	private static final String GCS_BLOB_FORMAT = "/gs/%s/%s";
	private BlobstoreService blobstoreService;

	public BlobstoreKeyFactory() {
		super();
		this.blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	}

	public BlobstoreKeyFactory(BlobstoreService blobstoreService) {
		super();
		this.blobstoreService = blobstoreService;
	}

	private String createGcsBlobUrl(GcsFilename filename) {
		String bucket = filename.getBucketName();
		String object = filename.getObjectName();
		String url = String.format(GCS_BLOB_FORMAT, bucket, object);
		return url;
	}

	private String createGcsBlobUrl(StorableFile file,
	                                String bucket) {
		String path = file.getFilePath();
		String url = String.format(GCS_BLOB_FORMAT, bucket, path);
		return url;
	}

	public BlobKey blobKeyForStorageRequest(GcsStorageFileRequest request) {
		GcsFilename filename = request.getGcsFilename();
		String url = this.createGcsBlobUrl(filename);
		BlobKey blobKey = this.blobstoreService.createGsBlobKey(url);
		return blobKey;
	}

	public BlobKey blobKeyForStorageFile(String bucket,
	                                 StorableFile file) {
		String url = this.createGcsBlobUrl(file, bucket);
		BlobKey blobKey = this.blobstoreService.createGsBlobKey(url);
		return blobKey;
	}

	public BlobstoreService getBlobstoreService() {
		return this.blobstoreService;
	}

	public void setBlobstoreService(BlobstoreService blobstoreService) {
		this.blobstoreService = blobstoreService;
	}
}
