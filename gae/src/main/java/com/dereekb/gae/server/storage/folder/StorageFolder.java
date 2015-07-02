package com.dereekb.gae.server.storage.folder;


/**
 * Default {@link StorableFolder} implementation.
 *
 * @author dereekb
 */
public class StorageFolder
        implements StorableFolder {

	private final String folderPath;

	public StorageFolder(String folderPath) throws IllegalArgumentException {
		if (folderPath == null) {
			throw new IllegalArgumentException("Folder Path cannot be null.");
		}

		this.folderPath = folderPath;
	}

	@Override
	public String getFolderPath() {
		return this.folderPath;
	}

}
