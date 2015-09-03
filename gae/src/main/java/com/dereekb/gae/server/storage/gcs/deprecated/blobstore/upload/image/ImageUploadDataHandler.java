package com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.image;

import com.dereekb.gae.server.storage.StorageAccessor;
import com.dereekb.gae.server.storage.file.StorableFile;
import com.dereekb.gae.server.storage.file.StorageFileContent;
import com.dereekb.gae.server.storage.gcs.blobstore.images.ImageEditor;
import com.dereekb.gae.server.storage.gcs.blobstore.images.ImageEditor.ImageEditorInstance;
import com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.UploadedBlobFile;
import com.dereekb.gae.server.storage.upload.deprecated.function.UploadFunctionPair;
import com.dereekb.gae.server.storage.upload.deprecated.function.observers.UploadFunctionDataObserverDelegate;
import com.google.appengine.api.images.Image;

/**
 * Implements UploadFunctionDataObserverDelegate and handles modifying and saving uploaded images.
 * 
 * @author dereekb
 *
 */
public class ImageUploadDataHandler<T>
        implements UploadFunctionDataObserverDelegate<T, UploadedBlobFile> {

	private ImageEditor editor;
	private ImageUploadDataHandlerDelegate<T> delegate;
	private StorageAccessor accessor;

	public ImageUploadDataHandler() {
		editor = new ImageEditor();
	}

	public ImageUploadDataHandler(ImageEditor editor,
	        ImageUploadDataHandlerDelegate<T> delegate,
	        StorageAccessor accessor) {
		super();
		this.editor = editor;
		this.delegate = delegate;
		this.accessor = accessor;
	}

	public void handleUploadedData(Iterable<UploadFunctionPair<T, UploadedBlobFile>> pairs) {

		for (UploadFunctionPair<T, UploadedBlobFile> pair : pairs) {
			UploadedBlobFile file = pair.getSource();
			T model = pair.getResult();

			boolean success = this.handleImageData(model, file);
			pair.setSuccessful(success);
		}

	}

	protected boolean handleImageData(T model,
	                                  UploadedBlobFile file) {
		byte[] bytes = file.getBytes();
		ImageEditorInstance instance = editor.forBytes(bytes);
		Image finalImage = delegate.modifyUploadedImage(instance);

		byte[] imageData = finalImage.getImageData();

		String contentType = file.getContentType();
		StorableFile storageFile = this.delegate.fileForUploadedImage(model, file);
		StorageFileContent storageFileData = new StorageFileContent(storageFile, imageData, contentType);
		boolean saveSuccess = this.accessor.saveFile(storageFileData);
		return saveSuccess;
	}

	public ImageEditor getEditor() {
		return editor;
	}

	public void setEditor(ImageEditor editor) {
		this.editor = editor;
	}

	public ImageUploadDataHandlerDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(ImageUploadDataHandlerDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public StorageAccessor getAccessor() {
		return accessor;
	}

	public void setAccessor(StorageAccessor accessor) {
		this.accessor = accessor;
	}

}
