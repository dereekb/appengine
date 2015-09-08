package com.dereekb.gae.server.storage.object.folder;

/**
 * Used for retrieving relative folder paths from input elements.
 *
 * @author dereekb
 *
 * @param <T>
 *            object type
 */
public interface StorableFolderResolver<T> {

	/**
	 * Returns the folder path for the input object.
	 *
	 * @param object
	 *            Input object. Never {@code null}.
	 * @return {@link StorableFolder} instance. Never {@code null}.
	 */
	public StorableFolder folderForObject(T object);

}
