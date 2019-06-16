package com.dereekb.gae.server.storage.object.folder.impl;

import com.dereekb.gae.server.deprecated.storage.exception.StorableObjectGenerationException;
import com.dereekb.gae.server.deprecated.storage.object.file.Storable;
import com.dereekb.gae.server.deprecated.storage.object.folder.StorableFolder;


/**
 * Default {@link StorableFolder} implementation.
 *
 * @author dereekb
 */
public class StorableFolderImpl
        implements StorableFolder {

	public static final String DEFAULT_FORMAT = "%s/%s";

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

	// MARK: StorableFolder
	@Override
	public String pathForFile(Storable storable) throws StorableObjectGenerationException {
		String fileName = storable.getFilename();
		return String.format(DEFAULT_FORMAT, this.folderPath, fileName);
	}

	@Override
	public String toString() {
		return "StorableFolderImpl [folderPath=" + this.folderPath + "]";
	}

}
