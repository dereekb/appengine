package com.dereekb.gae.server.storage.object.folder.impl;

import com.dereekb.gae.server.storage.object.folder.StorableFolder;


/**
 * Default {@link StorableFolder} implementation.
 *
 * @author dereekb
 */
public class StorableFolderImpl
        implements StorableFolder {

	private String folderPath;

	public StorableFolderImpl(String folderPath) throws IllegalArgumentException {
		this.setFolderPath(folderPath);
	}

	@Override
	public String getFolderPath() {
		return this.folderPath;
	}

	public void setFolderPath(String folderPath) {
		if (folderPath == null) {
			throw new IllegalArgumentException("Folder Path cannot be null.");
		}

		this.folderPath = folderPath;
	}

	@Override
	public String toString() {
		return "StorableFolderImpl [folderPath=" + this.folderPath + "]";
	}

}
