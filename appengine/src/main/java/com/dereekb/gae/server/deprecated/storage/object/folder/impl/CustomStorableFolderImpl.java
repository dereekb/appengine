package com.dereekb.gae.server.storage.object.folder.impl;

import com.dereekb.gae.server.deprecated.storage.exception.StorableObjectGenerationException;
import com.dereekb.gae.server.deprecated.storage.object.file.Storable;

/**
 * {@link StorableFolderImpl} extension with a custom folder format.
 *
 * @author dereekb
 */
public class CustomStorableFolderImpl extends StorableFolderImpl {

	private String format;

	public CustomStorableFolderImpl(String folderPath, String format) throws IllegalArgumentException {
		super(folderPath);
		this.setFormat(format);
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {

		if (format == null || format.isEmpty()) {
			throw new IllegalArgumentException("Format cannot be null or empty.");
		}

		this.format = format;
	}

	// MARK: StorableFolder
	@Override
	public String pathForFile(Storable storable) throws StorableObjectGenerationException {
		String fileName = storable.getFilename();
		String folderPath = this.getFolderPath();
		return String.format(this.format, folderPath, fileName);
	}

	@Override
	public String toString() {
		return "CustomStorableFolderImpl [format=" + this.format + ", getFolderPath()=" + this.getFolderPath() + "]";
	}

}
