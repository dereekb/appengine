package com.dereekb.gae.server.storage.file;

/**
 * Extension of storable data that include the content type and {@link StorageFileOptions} of the data being saved.
 * 
 * @author dereekb
 */
public interface StorableContent
        extends StorableData {

	/**
	 * Returns the content type. Generally a MIME type.
	 */
	public String getContentType();

	/**
	 * @return Returns (optional) options, or null if none are set/available.
	 */
	public StorageFileOptions getOptions();

}
