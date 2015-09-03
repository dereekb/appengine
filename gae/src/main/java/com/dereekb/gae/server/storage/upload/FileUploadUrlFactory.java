package com.dereekb.gae.server.storage.upload;

/**
 * Used for initializing and creating a url to upload to.
 *
 * @author dereekb
 *
 */
public interface FileUploadUrlFactory {

	/**
	 * Creates a new URL for uploading to.
	 *
	 * @param data
	 *            {@link String} value containing optional data related to
	 *            creating an upload.
	 * @return {@link String} containing a valid URL to upload to. Never
	 *         {@code null}.
	 */
	public String makeUploadUrl(String data);

}
