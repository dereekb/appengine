package com.dereekb.gae.server.storage.services.gcs;

import com.dereekb.gae.server.storage.object.file.StorableContent;
import com.dereekb.gae.server.storage.services.gcs.impl.GcsStorableFileRequestImpl;

/**
 * Used for converting a {@link StorableContent} instance into a
 * {@link GcsStorableFileSaveRequest} instance.
 *
 * @author dereekb
 *
 */
public interface GcsStorableFileSaveRequestBuilder {

	/**
	 * Converts the input request.
	 *
	 * @param gcsBucket
	 *            {@link String} of the Gcs Bucket name.
	 * @param storableContent
	 *            {@link StorableContent} converted to a
	 *            {@link GcsStorableFileRequestImpl}.
	 * @return {@link GcsStorableFileRequestImpl}. Never {@code null}.
	 */
	public GcsStorableFileSaveRequest buildRequest(String gcsBucket,
	                                               StorableContent content);

}
