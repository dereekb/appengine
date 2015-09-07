package com.dereekb.gae.server.storage.object.path.impl;

import com.dereekb.gae.server.storage.object.file.Storable;
import com.dereekb.gae.server.storage.object.folder.StorableFolder;
import com.dereekb.gae.server.storage.object.path.FilePathMerger;

/**
 * {@link FilePathMerger} implementation.
 *
 * @author dereekb
 */
public final class FilePathMergerImpl
        implements FilePathMerger {

	public static final String DEFAULT_FORMAT = "%s/%s";

	private String format;

	public FilePathMergerImpl() {
		this(FilePathMergerImpl.DEFAULT_FORMAT);
	}

	public FilePathMergerImpl(String format) throws IllegalArgumentException {
		this.format = format;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) throws IllegalArgumentException {

		if (format == null || format.isEmpty()) {
			throw new IllegalArgumentException("Format cannot be null or empty.");
		}

		this.format = format;
	}

	// MARK: FilePathMerger
	@Override
	public String makePath(StorableFolder folder,
	                       Storable storable) {
		String folderPath = folder.getFolderPath();
		String fileName = storable.getFilename();
		return String.format(this.format, folderPath, fileName);
	}

	public static final String makeFilePath(String folder,
	                                        String filename) {
		return String.format(DEFAULT_FORMAT, folder, filename);
	}

}
