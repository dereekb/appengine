package com.dereekb.gae.server.storage.services.blobstore.object.path;

import com.dereekb.gae.server.storage.exception.StorableObjectGenerationException;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * Used for retrieving a {@link BlobKey} for an input file.
 *
 * @author dereekb
 */
public interface BlobKeyResolver {

	/**
	 * Returns the {@link BlobKey} for the input file.
	 *
	 * @param file
	 *            {@link StorableFile} implementation.
	 * @return {@link BlobKey} for the input file. Never {@code null}.
	 * @throws StorableObjectGenerationException
	 *             thrown if the {@link BlobKey} could not be generated.
	 */
	public BlobKey blobKeyForFile(StorableFile file) throws StorableObjectGenerationException;

}
