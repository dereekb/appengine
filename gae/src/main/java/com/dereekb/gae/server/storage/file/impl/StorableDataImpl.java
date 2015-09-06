package com.dereekb.gae.server.storage.file.impl;

import java.util.Arrays;

import com.dereekb.gae.server.storage.file.StorableData;
import com.dereekb.gae.server.storage.file.StorableFile;

/**
 * {@link StorableData} implementation that wraps a {@link StorableFile} and
 * {@code byte[]} object.
 *
 * @author dereekb
 */
public class StorableDataImpl
        implements StorableData {

	private StorableFile file;
	private byte[] fileData;

	public StorableDataImpl(StorableFile file, byte[] bytes) throws IllegalArgumentException {
		this.file = file;
		this.fileData = bytes;
	}

	public StorableFile getFile() {
		return this.file;
	}

	public void setFile(StorableFile file) throws IllegalArgumentException {
		if (file == null) {
			throw new IllegalArgumentException("File cannot be null.");
		}

		this.file = file;
	}

	@Override
	public String getFilePath() {
		return this.file.getFilePath();
	}

	@Override
	public String getFilename() {
		return this.file.getFilename();
	}

	@Override
	public byte[] getFileData() {
		return this.fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	@Override
	public String toString() {
		return "StorableDataImpl [file=" + this.file + ", fileData=" + Arrays.toString(this.fileData) + "]";
	}

}
