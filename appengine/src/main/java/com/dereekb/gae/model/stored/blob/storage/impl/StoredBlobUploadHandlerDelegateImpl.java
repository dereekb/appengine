package com.dereekb.gae.model.stored.blob.storage.impl;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobType;
import com.dereekb.gae.model.stored.blob.storage.StoredBlobUploadHandlerDelegate;
import com.dereekb.gae.server.storage.object.file.StorableContent;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.object.file.StorableFilePathResolver;
import com.dereekb.gae.server.storage.object.file.impl.StorableContentImpl;

/**
 * {@link StoredBlobUploadHandlerDelegate} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            descriptor type
 */
public class StoredBlobUploadHandlerDelegateImpl<T extends Descriptor>
        implements StoredBlobUploadHandlerDelegate<T> {

	private StorableFilePathResolver<StoredBlob> fileResolver;

	public StoredBlobUploadHandlerDelegateImpl(StorableFilePathResolver<StoredBlob> fileResolver) {
		this.fileResolver = fileResolver;
	}

	public StorableFilePathResolver<StoredBlob> getFileResolver() {
		return this.fileResolver;
	}

	public void setFileResolver(StorableFilePathResolver<StoredBlob> fileResolver) {
		this.fileResolver = fileResolver;
	}

	// MARK: StoredBlobUploadHandlerDelegate
	@Override
	public StorableContent buildContent(byte[] bytes,
	                                    StoredBlob blob) throws RuntimeException {

		StorableFile file = this.fileResolver.resolveStorageFile(blob);
		StoredBlobType type = blob.getBlobType();
		String contentType = type.getMimeType();

		StorableContentImpl content = new StorableContentImpl(file, bytes, contentType);
		return content;
	}

	@Override
	public T createDescriptor(StoredBlob blob) throws RuntimeException {
		return null; // This implementation create a descriptor.
	}

	@Override
	public String toString() {
		return "StoredBlobUploadHandlerDelegateImpl [fileResolver=" + this.fileResolver + "]";
	}

}
