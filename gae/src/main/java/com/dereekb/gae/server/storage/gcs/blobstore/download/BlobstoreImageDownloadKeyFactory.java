package com.dereekb.gae.server.storage.gcs.blobstore.download;

import com.dereekb.gae.server.storage.download.StoredFileDownloadKeyService;
import com.dereekb.gae.server.storage.object.file.Storable;
import com.dereekb.gae.server.storage.services.blobstore.object.path.BlobstoreRelativeFilePathResolver;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

/**
 * Factory that implements the StoredFileDownloadKeyService interface, and
 * generates direct download links for the blobstore.
 *
 * Input storable items are treated as images.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class BlobstoreImageDownloadKeyFactory<T>
        implements StoredFileDownloadKeyService<T> {

	private Boolean crop;
	private Integer imageSize;
	private Boolean secure;

	private final ImagesService imageService = ImagesServiceFactory.getImagesService();
	private final BlobstoreRelativeFilePathResolver<T> resolver;

	public BlobstoreImageDownloadKeyFactory(BlobstoreRelativeFilePathResolver<T> resolver) {
		this.resolver = resolver;
	}

	private ServingUrlOptions optionsForBlobKey(BlobKey key) {
		ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(key);

		if (this.crop != null) {
			options = options.crop(this.crop);
		}

		if (this.imageSize != null) {
			options = options.imageSize(this.imageSize);
		}

		if (this.secure != null) {
			options = options.secureUrl(this.secure);
		}

		return options;
	}

	@Override
	public String makeDownloadKey(T source,
	                              Storable item) {

		BlobKey key = this.resolver.blobKeyForFile(source, item);
		ServingUrlOptions options = this.optionsForBlobKey(key);
		String downloadUrl = this.imageService.getServingUrl(options);
		return downloadUrl;
	}

	public ImagesService getImageService() {
		return this.imageService;
	}

	public Boolean getCrop() {
		return this.crop;
	}

	public void setCrop(Boolean crop) {
		this.crop = crop;
	}

	public Integer getImageSize() {
		return this.imageSize;
	}

	public void setImageSize(Integer imageSize) {
		this.imageSize = imageSize;
	}

	public Boolean getSecure() {
		return this.secure;
	}

	public void setSecure(Boolean secure) {
		this.secure = secure;
	}

	public BlobstoreRelativeFilePathResolver<T> getResolver() {
		return this.resolver;
	}

}
