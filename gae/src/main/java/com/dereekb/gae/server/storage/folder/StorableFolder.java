package com.dereekb.gae.server.storage.folder;


/**
 * Represents a file folder. Contains it's path.
 *
 * @author dereekb
 *
 */
public interface StorableFolder {

	/**
	 * @return String path to folder. Paths should end with "/".
	 */
	public String getFolderPath();

}
