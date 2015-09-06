package com.dereekb.gae.server.storage.services.gcs;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;

/**
 * Save Request used by {@link GcsStorageSystem}.
 *
 * @author dereekb
 */
public interface GcsStorableFileSaveRequest
        extends GcsStorableFileRequest {

	/**
	 *
	 * @return {@link GcsFileOptions} for this file. Never {@code null}.
	 */
	public GcsFileOptions getGcsFileOptions();

	/**
	 *
	 * @return {@code byte[]} data. Never {@code null}.
	 */
	public byte[] getFileData();

}
