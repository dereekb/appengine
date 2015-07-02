package com.dereekb.gae.server.storage;

import java.io.IOException;

import com.dereekb.gae.server.storage.file.StorableData;
import com.dereekb.gae.server.storage.file.StorableFile;


public interface ReadOnlyStorageAccessor {

	/**
	 * Loads the given file using the Accessor's default options.
	 *
	 * @param file
	 * @return StorableData object containing the loaded file.
	 * @throws IOException
	 */
	public StorableData loadFile(StorableFile file) throws IOException;

	/**
	 * Checks to see if a file exists in the datastore.
	 *
	 * @param file
	 * @return Returns true if the file exists.
	 * @throws IOException
	 */
	public boolean fileExists(StorableFile file) throws IOException;

}
