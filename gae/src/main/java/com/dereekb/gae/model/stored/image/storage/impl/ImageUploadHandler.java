package com.dereekb.gae.model.stored.image.storage.impl;

import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.storage.StoredBlobUploadHandlerDelegate;
import com.dereekb.gae.model.stored.blob.storage.impl.StoredBlobUploadHandlerDelegateImpl;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.StoredImageType;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.storage.object.file.StorableFilePathResolver;
import com.googlecode.objectify.Key;

/**
 * {@link StoredBlobUploadHandlerDelegate} for uploading {@link StoredImage}
 * instances.
 *
 * @author dereekb
 *
 */
public class ImageUploadHandler extends StoredBlobUploadHandlerDelegateImpl<StoredImage> {

	private StoredImageType imageType;
	private Setter<StoredImage> imageSetter;

	public ImageUploadHandler(StorableFilePathResolver<StoredBlob> fileResolver) {
		super(fileResolver);
	}

	public ImageUploadHandler(StoredImageType imageType,
	        Setter<StoredImage> imageSetter,
	        StorableFilePathResolver<StoredBlob> fileResolver) {
		super(fileResolver);
		this.imageType = imageType;
		this.imageSetter = imageSetter;
	}

	public StoredImageType getImageType() {
		return this.imageType;
	}

	public void setImageType(StoredImageType imageType) {
		this.imageType = imageType;
	}

	public Setter<StoredImage> getImageSetter() {
		return this.imageSetter;
	}

	public void setImageSetter(Setter<StoredImage> imageSetter) {
		this.imageSetter = imageSetter;
	}

	// MARK: StoredBlobUploadedHandlerDelegate
	@Override
	public StoredImage createDescriptor(StoredBlob blob) throws RuntimeException {
		StoredImage image = this.buildImageForBlob(blob);

		this.imageSetter.save(image, false);

		return image;
	}

	private StoredImage buildImageForBlob(StoredBlob blob) {
		StoredImage image = new StoredImage();

		String filename = blob.getFilename();
		image.setName(filename);

		Key<StoredBlob> storedBlobKey = blob.getObjectifyKey();
		image.setBlob(storedBlobKey);
		image.setType(this.imageType);

		return image;
	}

}
