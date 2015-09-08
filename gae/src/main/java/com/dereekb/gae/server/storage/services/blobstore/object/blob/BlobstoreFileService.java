package com.dereekb.gae.server.storage.services.blobstore.object.blob;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.dereekb.gae.server.storage.services.gcs.object.file.GcsStorableFile;
import com.google.appengine.api.blobstore.BlobKey;

/**
 * Service for serving blobs directly.
 *
 * @author dereekb
 *
 */
public interface BlobstoreFileService {

	/**
	 * Serves a blob through a blobKey's string representation.
	 *
	 * @param blobKey
	 *            {@link String} of the blob key's representation. Never
	 *            {@code null}.
	 * @param resp
	 *            {@link HttpServletResponse} to respond on. Never {@code null}.
	 * @throws IOException
	 */
	public void serveStorageBlob(String blobKey,
	                             HttpServletResponse resp) throws IOException;

	/**
	 * Serves a blob form the input {@link GcsStorableFile}.
	 *
	 * @param file
	 *            {@link GcsStorableFile} to serve. Never {@code null}.
	 * @param resp
	 *            {@link HttpServletResponse} to respond on. Never {@code null}.
	 * @throws IOException
	 */
	public void serveStorageBlob(GcsStorableFile file,
	                             HttpServletResponse resp) throws IOException;

	/**
	 *
	 * @param blobKey
	 *            {@link BlobKey} of file to serve. Never {@code null}.
	 * @param resp
	 *            {@link HttpServletResponse} to respond on. Never {@code null}.
	 * @throws IOException
	 */
	public void serveStorageBlob(BlobKey blobKey,
	                             HttpServletResponse resp) throws IOException;

}
