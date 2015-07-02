package com.dereekb.gae.server.storage.upload;

/**
 * Factory for generating URLs for uploading data.
 * 
 * @author dereekb
 *
 */
public interface FileUploadUrlFactory {

	/**
	 * Creates a new upload url that forwards to a default url when finished.
	 * 
	 * @return Upload URL
	 */
	public String newUploadUrl();

	/**
	 * Creates a new upload url that forwards to the specified url when finished.
	 * 
	 * @param forwardUrl
	 * @return Upload URL
	 */
	public String newUploadUrl(String forwardUrl);

}
