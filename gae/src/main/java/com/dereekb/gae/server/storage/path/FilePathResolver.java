package com.dereekb.gae.server.storage.path;

import com.dereekb.gae.server.storage.file.Storable;
import com.dereekb.gae.server.storage.file.StorableFile;

/**
 * Used for generating a {@link StorableFile} using an input {@link Storable}
 * file.
 *
 * @author dereekb
 */
public interface FilePathResolver {

	/**
	 * Generates a filepath for the input {@link Storable}.
	 *
	 * @param reference
	 *            {@link Storable} object containing a filename.
	 * @return {@link StorableFile} instance for the reference.
	 */
	public StorableFile pathForStorable(Storable reference);

}
