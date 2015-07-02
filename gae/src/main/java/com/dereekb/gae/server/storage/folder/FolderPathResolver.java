package com.dereekb.gae.server.storage.folder;

/**
 * Used for retrieving relative folder paths from input elements.
 *
 * @author dereekb
 * @param <T>
 */
public interface FolderPathResolver<T> {

	/**
	 * Returns the folder path for the input object.
	 *
	 * @param object
	 * @return {@link StorableFolder} instance.
	 */
	public StorableFolder folderForObject(T object);

}
