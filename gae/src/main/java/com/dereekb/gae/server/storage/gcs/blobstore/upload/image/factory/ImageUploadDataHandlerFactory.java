package com.dereekb.gae.server.storage.gcs.blobstore.upload.image.factory;

import com.dereekb.gae.server.storage.StorageAccessor;
import com.dereekb.gae.server.storage.gcs.blobstore.images.ImageEditor;
import com.dereekb.gae.server.storage.gcs.blobstore.upload.UploadedBlobFile;
import com.dereekb.gae.server.storage.gcs.blobstore.upload.image.ImageUploadDataHandler;
import com.dereekb.gae.server.storage.gcs.blobstore.upload.image.ImageUploadDataHandlerDelegate;
import com.dereekb.gae.server.storage.upload.function.factory.UploadFunctionDataObserverDelegateFactory;
import com.dereekb.gae.server.storage.upload.function.observers.UploadFunctionDataObserverDelegate;

public class ImageUploadDataHandlerFactory<T>
        implements UploadFunctionDataObserverDelegateFactory<T, UploadedBlobFile> {

	private ImageEditor editor;
	private ImageUploadDataHandlerDelegate<T> delegate;
	private StorageAccessor accessor;

	@Override
	public UploadFunctionDataObserverDelegate<T, UploadedBlobFile> makeDataObserverDelegate() {

		ImageUploadDataHandler<T> handler = new ImageUploadDataHandler<T>();

		if (editor != null) {
			handler.setEditor(editor);
		}

		if (delegate != null) {
			handler.setDelegate(delegate);
		}

		if (accessor != null) {
			handler.setAccessor(accessor);
		}

		return handler;
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
