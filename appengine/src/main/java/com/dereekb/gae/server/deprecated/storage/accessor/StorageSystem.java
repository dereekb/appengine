package com.dereekb.gae.server.storage.accessor;


/**
 * Accessor for reading and writing to storage.
 *
 * @author dereekb
 */
public interface StorageSystem
        extends StorageReader, StorageWriter {

	/**
	 * Returns the name of the storage system.
	 *
	 * @return {@link String} of the name. Never {@code null}.
	 */
	public String getStorageSystemName();

}