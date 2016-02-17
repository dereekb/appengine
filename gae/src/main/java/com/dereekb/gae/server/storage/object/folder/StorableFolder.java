package com.dereekb.gae.server.storage.object.folder;

import com.dereekb.gae.server.storage.exception.StorableObjectGenerationException;
import com.dereekb.gae.server.storage.object.file.Storable;

/**
 * Represents a folder path.
 *
 * @author dereekb
 *
 */
public interface StorableFolder {

	/**
	 * Returns the path to the folder.
	 * <p>
	 * Paths should end with "/".
	 * </p>
	 *
	 * @return {@link String} containing the path. Never {@code null}.
	 * @throws StorableObjectGenerationException
	 *             thrown if the folder path cannot be created.
	 */
	public String getFolderPath() throws StorableObjectGenerationException;

	/**
	 * Returns the file path for the input file.
	 *
	 * @param storable
	 *            {@link Storable} file. Never {@code null}.
	 * @return {@link String} containing the file path. Never {@code null}.
	 * @throws StorableObjectGenerationException
	 *             thrown if the file path cannot be created.
	 */
	public String pathForFile(Storable storable) throws StorableObjectGenerationException;

}
