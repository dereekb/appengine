package com.dereekb.gae.server.storage.services.blobstore.upload;

import com.dereekb.gae.server.storage.upload.FileUploadUrlFactory;
import com.dereekb.gae.utilities.misc.bit.ByteSizeUtility;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;

/**
 * {@link FileUploadUrlFactory} implementation for the Blobstore.
 *
 * @author dereekb
 *
 */
public class BlobstoreFileUploadUrlFactory
        implements FileUploadUrlFactory {

	private static final Long DEFAULT_MAX_UPLOAD_SIZE = ByteSizeUtility.megaBytes(3);
	private static final Long DEFAULT_MAX_BLOB_SIZE = ByteSizeUtility.megaBytes(1);

	private static final String DEFAULT_URL_FORMAT = "%s/%s";

	private final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private Long maxBlobSize = DEFAULT_MAX_UPLOAD_SIZE;
	private Long maxUploadSize = DEFAULT_MAX_BLOB_SIZE;

	private String uploadBucket;

	private String urlFormat = DEFAULT_URL_FORMAT;

	private String baseUrl;

	public BlobstoreFileUploadUrlFactory(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public BlobstoreFileUploadUrlFactory(String baseUrl, String uploadBucket) {
		this.baseUrl = baseUrl;
		this.uploadBucket = uploadBucket;
	}

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

	// MARK: FileUploadUrlFactory
	@Override
	public String makeUploadUrl(String data) {
		UploadOptions options = this.createUploadOptions();
		String forwardUrl = this.makeForwardUrl(data);
		return this.blobstoreService.createUploadUrl(forwardUrl, options);
	}

	private String makeForwardUrl(String data) {
		return String.format(this.urlFormat, this.baseUrl, data);
	}

	@Override
	public String toString() {
		return "BlobstoreFileUploadUrlFactory [blobstoreService=" + this.blobstoreService + ", maxBlobSize="
		        + this.maxBlobSize + ", maxUploadSize=" + this.maxUploadSize + ", uploadBucket=" + this.uploadBucket
		        + ", urlFormat=" + this.urlFormat + ", baseUrl=" + this.baseUrl + "]";
	}

}
