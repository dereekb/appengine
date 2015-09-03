package com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload;

import com.dereekb.gae.server.storage.upload.deprecated.FileUploadUrlFactory;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;

public class BlobstoreUrlFactory
        implements FileUploadUrlFactory {

	private static final Long DEFAULT_MAX_UPLOAD_SIZE = (1024 * 1024L); // 1MB default upload size.
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	/**
	 * String to forward to when finished.
	 */
	private String forwardUrl;

	/**
	 * Optional bucket to upload to.
	 */
	private String uploadBucket;

	/**
	 * Max size of a single blob.
	 */
	private Long maxBlobSize = DEFAULT_MAX_UPLOAD_SIZE;

	/**
	 * Max size of all blobs uploaded.
	 */
	private Long maxUploadSize = DEFAULT_MAX_UPLOAD_SIZE;

	private UploadOptions createUploadOptions() {
		UploadOptions options = null;

		if (this.uploadBucket != null) {
			options = UploadOptions.Builder.withGoogleStorageBucketName(this.uploadBucket);
		} else {
			options = UploadOptions.Builder.withDefaults();
		}

		if (this.maxBlobSize != null) {
			options = options.maxUploadSizeBytesPerBlob(this.maxBlobSize);
		}

		if (this.maxUploadSize != null) {
			options = options.maxUploadSizeBytes(this.maxUploadSize);
		}

		return options;
	}

	@Override
	public String newUploadUrl() {
		return this.newUploadUrl(this.forwardUrl);
	}

	@Override
	public String newUploadUrl(String forwardUrl) {
		UploadOptions options = this.createUploadOptions();
		String uploadUrl = this.blobstoreService.createUploadUrl(forwardUrl, options);
		return uploadUrl;
	}

	public String getUploadBucket() {
		return uploadBucket;
	}

	public void setUploadBucket(String uploadBucket) {
		this.uploadBucket = uploadBucket;
	}

	public BlobstoreService getBlobstoreService() {
		return blobstoreService;
	}

	public void setBlobstoreService(BlobstoreService blobstoreService) throws NullPointerException {
		if (blobstoreService == null) {
			throw new NullPointerException();
		}

		this.blobstoreService = blobstoreService;
	}

	public Long getMaxBlobSize() {
		return maxBlobSize;
	}

	public void setMaxBlobSize(Long maxBlobSize) {
		this.maxBlobSize = maxBlobSize;
	}

	public Long getMaxUploadSize() {
		return maxUploadSize;
	}

	public void setMaxUploadSize(Long maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

}
