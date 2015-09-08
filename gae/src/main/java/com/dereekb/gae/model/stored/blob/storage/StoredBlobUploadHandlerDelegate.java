package com.dereekb.gae.model.stored.blob.storage;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.storage.StoredBlobUploadHandler.Instance;
import com.dereekb.gae.server.storage.object.file.StorableContent;

/**
 * {@link StoredBlobUploadHandler} delegate.
 *
 * @author dereekb
 *
 * @param <T>
 *            descriptor type
 */
public interface StoredBlobUploadHandlerDelegate<T extends Descriptor> {

	/**
	 *
	 * @param instance
	 *            {@link Instance}. Never {@code null}.
	 * @return {@link StorableContent} to save for the instance. Never
	 *         {@code null}.
	 * @throws RuntimeException
	 *             if the {@link StorableContent} cannot be generated.
	 */
	public StorableContent buildContent(byte[] bytes,
	                                    StoredBlob blob) throws RuntimeException;

	/**
	 *
	 * @param blob
	 *            {@link StoredBlob} that was created.
	 * @return {@link Descriptor} for {@code blob}. Returns {@code null} if no
	 *         descriptor needs to be generated.
	 * @throws RuntimeException
	 *             if the {@link Descriptor} cannot be generated.
	 */
	public T createDescriptor(StoredBlob blob) throws RuntimeException;

}
