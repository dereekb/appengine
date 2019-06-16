package com.dereekb.gae.model.stored.blob.dto;

import com.dereekb.gae.model.deprecated.stored.blob.StoredBlob;

/**
 * Interface for types that can generate a download link for the input
 * {@link StoredBlob}.
 *
 * @author dereekb
 */
public interface StoredBlobDataDownloadBuilder {

	public String downloadLinkForStoredBlob(StoredBlob blob);

}
