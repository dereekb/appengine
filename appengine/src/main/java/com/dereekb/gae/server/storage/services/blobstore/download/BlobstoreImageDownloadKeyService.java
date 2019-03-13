package com.dereekb.gae.server.storage.services.blobstore.download;

import com.dereekb.gae.server.storage.download.DownloadKeyService;
import com.dereekb.gae.server.storage.exception.MissingFileException;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.services.blobstore.object.path.BlobKeyResolver;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

/**
 * {@link DownloadKeyService} implementation.
 * <p>
 * Can be configured with options for a {@link ServingUrlOptions} that are added
 * to each url generated through the instance.
 * </p>
 *
 * @author dereekb
 *
 */
public class BlobstoreImageDownloadKeyService extends BlobstoreDownloadKeyService {

	private ImagesService imageService;
	private BlobKeyResolver resolver;

	private Boolean crop;
	private Integer size;
	private Boolean secure;

	public BlobstoreImageDownloadKeyService(BlobKeyResolver resolver) {
		super(resolver);
		this.imageService = ImagesServiceFactory.getImagesService();
	}

	public BlobstoreImageDownloadKeyService(ImagesService imageService, BlobKeyResolver resolver) {
		super(resolver);
		this.imageService = imageService;
	}

	public BlobstoreImageDownloadKeyService(ImagesService imageService,
	        BlobKeyResolver resolver,
	        Boolean crop,
	        Integer size,
	        Boolean secure) {
		super(resolver);
		this.imageService = imageService;
		this.crop = crop;
		this.size = size;
		this.secure = secure;
	}

	public ImagesService getImageService() {
		return this.imageService;
	}

	public void setImageService(ImagesService imageService) {
		this.imageService = imageService;
	}

	@Override
    public BlobKeyResolver getResolver() {
		return this.resolver;
	}

	@Override
    public void setResolver(BlobKeyResolver resolver) {
		this.resolver = resolver;
	}

	public Boolean getCrop() {
		return this.crop;
	}

	public void setCrop(Boolean crop) {
		this.crop = crop;
	}

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Boolean getSecure() {
		return this.secure;
	}

	public void setSecure(Boolean secure) {
		this.secure = secure;
	}

	// MARK: DownloadKeyService
	@Override
	public String makeDownloadKey(StorableFile file) throws MissingFileException {
		BlobKey key = this.resolver.blobKeyForFile(file);
		ServingUrlOptions options = this.optionsForBlobKey(key);
		String downloadUrl = this.imageService.getServingUrl(options);
		return downloadUrl;
	}

	private ServingUrlOptions optionsForBlobKey(BlobKey key) {
		ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(key);

		if (this.crop != null) {
			options = options.crop(this.crop);
		}

		if (this.size != null) {
			options = options.imageSize(this.size);
		}

		if (this.secure != null) {
			options = options.secureUrl(this.secure);
		}

		return options;
	}

	@Override
	public String toString() {
		return "BlobstoreImageDownloadKeyService [imageService=" + this.imageService + ", resolver=" + this.resolver
		        + ", crop=" + this.crop + ", size=" + this.size + ", secure=" + this.secure + "]";
	}

}
