package com.dereekb.gae.server.storage.services.blobstore.object.blob;

import java.io.IOException;

import com.google.appengine.api.blobstore.BlobKey;

/**
 * Used for reading data for uploaded blobs.
 *
 * @author dereekb
 *
 */
public interface BlobstoreBlobReader {

	/**
	 * Reads data corresponding to the {@link BlobKey}.
	 *
	 * @param key
	 *            {@link BlobKey} value. Never {@code null}.
	 * @param size
	 *            {@link Long} value corresponding to the blob size. Generally
	 *            this information is retrieved though blob file info. Never
	 *            {@code null}.
	 * @return {@code byte} array. Never {@code null}.
	 * @throws IOException
	 *             If the data cannot be read.
	 */
	public byte[] readBlobBytes(BlobKey key,
	                            Long size) throws IOException;

}
