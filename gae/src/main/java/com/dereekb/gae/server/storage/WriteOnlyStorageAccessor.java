package com.dereekb.gae.server.storage;

import com.dereekb.gae.server.storage.file.StorableContent;
import com.dereekb.gae.server.storage.file.StorableFile;


public interface WriteOnlyStorageAccessor {

	/**
	 * Saves the given file using the Accessor's default options.
	 *
	 * @param file
	 * @return True if the save is successful.
	 */
	public boolean saveFile(StorableContent file);

	/**
	 * Deletes a file from the datastore using the Accessor's default options.
	 *
	 * @param file
	 * @return True if the delete is successful.
	 */
	public boolean deleteFile(StorableFile file);

}
