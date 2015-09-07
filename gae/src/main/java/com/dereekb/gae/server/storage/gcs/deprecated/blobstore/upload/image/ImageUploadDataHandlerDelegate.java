package com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.image;

import com.dereekb.gae.server.storage.gcs.blobstore.images.ImageEditor.ImageEditorInstance;
import com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.UploadedBlobFile;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.google.appengine.api.images.Image;

public interface ImageUploadDataHandlerDelegate<T> {

	/**
	 * Modifies the image in the ImageEditorInstance.
	 * 
	 * @param instance
	 * @return The modified image, or original image if no modification is necessary.
	 */
	public Image modifyUploadedImage(ImageEditorInstance instance);

	public StorableFile fileForUploadedImage(T item,
	                                         UploadedBlobFile file);

}
