package com.dereekb.gae.server.storage.object.file;

import com.dereekb.gae.server.deprecated.storage.exception.StorableObjectGenerationException;

/**
 * Used to retrieve a {@link StorableFile} from an input object.
 *
 * @author dereekb
 *
 */
public interface StorableFilePathResolver<T> {

	/**
	 * Gets the {@link StorableFile} path for the input object.
	 *
	 * @param object
	 *            Object. Never {@code null}.
	 * @return {@link StorableFile} for the object. Never {@code null}.
	 * @throws StorableObjectGenerationException
	 *             thrown if the file cannot be resolved.
	 */
	public StorableFile resolveStorageFile(T object) throws StorableObjectGenerationException;

}
