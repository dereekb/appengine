package com.dereekb.gae.server.storage.object.file.impl;

import com.dereekb.gae.server.storage.object.file.StorableFile;

/**
 * Basic storage file that implements the {@link StorableFile} interface.
 *
 * It contains a filename and the full path to that file.
 *
 * @author dereekb
 *
 */
public class StorableFileImpl
        implements StorableFile {

	private final String filename;
	private final String path;

	public StorableFileImpl(String filename, String path) {
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
