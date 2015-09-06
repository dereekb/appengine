package com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.image.factory;

import com.dereekb.gae.server.storage.accessor.StorageSystem;
import com.dereekb.gae.server.storage.gcs.blobstore.images.ImageEditor;
import com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.UploadedBlobFile;
import com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.image.ImageUploadDataHandler;
import com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.image.ImageUploadDataHandlerDelegate;
import com.dereekb.gae.server.storage.upload.deprecated.function.factory.UploadFunctionDataObserverDelegateFactory;
import com.dereekb.gae.server.storage.upload.deprecated.function.observers.UploadFunctionDataObserverDelegate;

@Deprecated
public class ImageUploadDataHandlerFactory<T>
        implements UploadFunctionDataObserverDelegateFactory<T, UploadedBlobFile> {

	private ImageEditor editor;
	private ImageUploadDataHandlerDelegate<T> delegate;
	private StorageSystem accessor;

	@Override
	public UploadFunctionDataObserverDelegate<T, UploadedBlobFile> makeDataObserverDelegate() {

		ImageUploadDataHandler<T> handler = new ImageUploadDataHandler<T>();

		if (this.editor != null) {
			handler.setEditor(this.editor);
		}

		if (this.delegate != null) {
			handler.setDelegate(this.delegate);
		}

		if (this.accessor != null) {
			handler.setAccessor(this.accessor);
		}

		return handler;
	}

	public ImageEditor getEditor() {
		return this.editor;
	}

	public void setEditor(ImageEditor editor) {
		this.editor = editor;
	}

	public ImageUploadDataHandlerDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ImageUploadDataHandlerDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public StorageSystem getAccessor() {
		return this.accessor;
	}

	public void setAccessor(StorageSystem accessor) {
		this.accessor = accessor;
	}

}
