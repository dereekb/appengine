package com.dereekb.gae.server.storage.download;


/**
 * Creates a download URL for the given model.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public interface FileDownloadUrlFactory<T> {

	public String makeDownloadUrl(T object);

}
