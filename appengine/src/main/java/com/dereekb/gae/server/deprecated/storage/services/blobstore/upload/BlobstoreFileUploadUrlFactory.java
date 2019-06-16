package com.dereekb.gae.server.storage.services.blobstore.upload;

import java.util.Set;

import com.dereekb.gae.server.deprecated.storage.upload.FileUploadUrlFactory;
import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;
import com.dereekb.gae.utilities.misc.bit.ByteSizeUtility;
import com.dereekb.gae.web.api.model.extension.upload.exception.InvalidUploadTypeException;
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

	private Set<String> types;

	public BlobstoreFileUploadUrlFactory(String urlFormat, String uploadBucket, Set<String> types) {
		this.setUrlFormat(urlFormat);
		this.setUploadBucket(uploadBucket);
		this.setTypes(types);
	}

	public Long getMaxBlobSize() {
		return this.maxBlobSize;
	}

	public void setMaxBlobSize(Long maxBlobSize) {
		this.maxBlobSize = maxBlobSize;
	}

	public Long getMaxUploadSize() {
		return this.maxUploadSize;
	}

	public void setMaxUploadSize(Long maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}

	public String getUploadBucket() {
		return this.uploadBucket;
	}

	public void setUploadBucket(String uploadBucket) {
		this.uploadBucket = uploadBucket;
	}

	public String getUrlFormat() {
		return this.urlFormat;
	}

	public void setUrlFormat(String urlFormat) {
		this.urlFormat = urlFormat;
	}

	public BlobstoreService getBlobstoreService() {
		return this.blobstoreService;
	}

	public Set<String> getTypes() {
		return this.types;
	}

	public void setTypes(Set<String> types) {
		this.types = new CaseInsensitiveSet(types);
	}

	// MARK: FileUploadUrlFactory
	@Override
	public String makeUploadUrl(String type) throws InvalidUploadTypeException {
		if (type == null || this.types.contains(type) == false) {
			throw new InvalidUploadTypeException(type);
		}

		UploadOptions options = this.createUploadOptions();
		String forwardUrl = this.makeForwardUrl(type);
		return this.blobstoreService.createUploadUrl(forwardUrl, options);
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

	private String makeForwardUrl(String type) {
		return String.format(this.urlFormat, type);
	}

	@Override
	public String toString() {
		return "BlobstoreFileUploadUrlFactory [blobstoreService=" + this.blobstoreService + ", maxBlobSize="
		        + this.maxBlobSize + ", maxUploadSize=" + this.maxUploadSize + ", uploadBucket=" + this.uploadBucket
		        + ", urlFormat=" + this.urlFormat + "]";
	}

}
