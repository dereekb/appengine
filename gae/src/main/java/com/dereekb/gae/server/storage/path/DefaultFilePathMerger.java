package com.dereekb.gae.server.storage.path;

import com.dereekb.gae.server.storage.file.Storable;
import com.dereekb.gae.server.storage.folder.StorableFolder;

/**
 * Default implementation of {@link FilePathMerger}.
 *
 * @author dereekb
 */
public final class DefaultFilePathMerger
        implements FilePathMerger {

	public static final String DEFAULT_FORMAT = "%s/%s";

	private final String format;

	public DefaultFilePathMerger() {
		this(DefaultFilePathMerger.DEFAULT_FORMAT);
	}

	public DefaultFilePathMerger(String format) {
		this.format = format;
	}

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
