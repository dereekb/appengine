package com.dereekb.gae.server.storage;


/**
 * Basic storage accessor able to save/load files from a file storage.
 *
 * @author dereekb
 */
public interface StorageAccessor
        extends ReadOnlyStorageAccessor, WriteOnlyStorageAccessor {

	/**
	 * Returns the name of the storage system.
	 *
	 * @return name of the storage system. Never null.
	 */
	public String getStorageSystemName();

}