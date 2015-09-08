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
