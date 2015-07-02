package com.dereekb.gae.server.storage.file;

/**
 * Basic storage file that implements the {@link StorableFile} interface.
 *
 * It contains a filename and the full path to that file.
 *
 * @author dereekb
 *
 */
public class StorageFile
        implements StorableFile {

	private final String filename;
	private final String path;

	public StorageFile(String filename, String path) {
		this.filename = filename;
		this.path = path;
	}

	@Override
    public String getFilename() {
		return this.filename;
	}

	@Override
    public String getFilePath() {
		return this.path;
	}

}
