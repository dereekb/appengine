package com.dereekb.gae.server.storage.object.folder;


/**
 * Represents a folder.
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
	 */
	public String getFolderPath();

}
