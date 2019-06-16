package com.dereekb.gae.server.storage.accessor;

import java.io.IOException;

import com.dereekb.gae.server.deprecated.storage.object.file.StorableContent;
import com.dereekb.gae.server.deprecated.storage.object.file.StorableFile;

/**
 * Used to read {@link StorableFile} data.
 *
 * @author dereekb
 *
 */
public interface StorageReader {

	/**
	 * Loads a file from storage.
	 *
	 * @param file
	 *            {@link StorableFile}. Never {@code null}.
	 * @return {@link StorableContent} corresponding to {@code file}. Never
	 *         {@code null}.
	 * @throws IOException
	 *             Thrown if the file fails to load.
	 */
	public StorableContent loadFile(StorableFile file) throws IOException;

	/**
	 * Returns true if the file exists.
	 *
	 * @param file
	 *            {@link StorableFile}. Never {@code null}.
	 * @return {@code true} if the file exists.
	 * @throws IOException
	 *             Thrown if the file fails to load.
	 */
	public boolean fileExists(StorableFile file) throws IOException;

}
