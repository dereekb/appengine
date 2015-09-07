package com.dereekb.gae.server.storage.services.blobstore.object.path;

import com.dereekb.gae.server.storage.object.file.Storable;
import com.google.appengine.api.blobstore.BlobKey;


/**
 * Used for retrieving a {@link BlobKey} for a reference file that is
 * represented by a {@link Storedable} element.
 *
 * @author dereekb
 */
public interface BlobstoreRelativeFilePathResolver<T> {

	/**
	 * Generates a {@link BlobKey} for the {@link Storable} element.
	 *
	 * @param relative
	 *            Object relative to the reference element.
	 * @param reference
	 *            In-memory representation of the reference object.
	 * @return A {@link BlobKey} for the reference element.
	 */
	public BlobKey blobKeyForFile(T relative,
	                              Storable reference);

}
