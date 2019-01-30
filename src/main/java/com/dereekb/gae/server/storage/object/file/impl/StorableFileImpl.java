package com.dereekb.gae.server.storage.object.file.impl;

import com.dereekb.gae.server.storage.object.file.Storable;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.object.folder.StorableFolder;

/**
 * Basic storage file that implements the {@link StorableFile} interface.
 * <p>
 * It contains a filename and the full filePath to that file.
 *
 * @author dereekb
 *
 */
public class StorableFileImpl
        implements StorableFile {

	private String filename;
	private String filePath;

	public StorableFileImpl(String filename, String filePath) {
		this.filename = filename;
		this.filePath = filePath;
	}

	public StorableFileImpl(Storable storable, StorableFolder folder) {
		this.filename = storable.getFilename();
		this.filePath = folder.pathForFile(storable);
	}

    @Override
    public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "StorableFileImpl [filename=" + this.filename + ", filePath=" + this.filePath + "]";
	}

}
