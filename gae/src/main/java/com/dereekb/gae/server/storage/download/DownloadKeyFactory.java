package com.dereekb.gae.server.storage.download;

import com.dereekb.gae.server.storage.exception.MissingFileException;
import com.dereekb.gae.server.storage.object.file.StorableFile;

/**
 * Used for generating download keys/urls for {@link StorableFile} instances.
 *
 * @author dereekb
 */
public interface DownloadKeyFactory {

	/**
	 * Generates a new download key for the given item.
	 * <p>
	 * The keys themselves may not mean anything by themselves, and do not
	 * necessarily explain what service they are for.
	 * </p>
	 *
	 * @param file
	 *            {@link StorableFile}. Never {@code null}.
	 * @return {@link String} of data. Never {@code null}.
	 * @throws MissingFileException
	 *             thrown if the file does not exist, or the key could not be
	 *             created.
	 */
	public String makeDownloadKey(StorableFile file) throws MissingFileException;

}
