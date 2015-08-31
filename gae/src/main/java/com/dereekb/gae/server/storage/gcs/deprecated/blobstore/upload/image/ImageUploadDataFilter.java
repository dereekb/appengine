package com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.image;

import com.dereekb.gae.server.storage.gcs.blobstore.images.ImageValidator;
import com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.UploadedBlobFile;
import com.dereekb.gae.server.storage.upload.function.filters.UploadFunctionDataFilterDelegate;

/**
 * Handles filtering uploaded images.
 * 
 * @author dereekb
 */
public class ImageUploadDataFilter<T>
        implements UploadFunctionDataFilterDelegate<UploadedBlobFile> {

	public ImageValidator validator;

	public ImageUploadDataFilter() {}

	public ImageUploadDataFilter(ImageValidator validator) {
		super();
		this.validator = validator;
	}

	@Override
	public boolean isValidData(UploadedBlobFile file) {
		byte[] bytes = file.getBytes();
		boolean isValid = validator.validateImage(bytes);
		return isValid;
	}

	public ImageValidator getValidator() {
		return validator;
	}

	public void setValidator(ImageValidator validator) {
		this.validator = validator;
	}

}
