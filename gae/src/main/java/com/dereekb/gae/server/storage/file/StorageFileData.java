package com.dereekb.gae.server.storage.file;

/**
 * Basic class that implements StorableData by wrapping a StorableFile and pairing it with a bytes array.
 * 
 * @author dereekb
 */
public class StorageFileData
        implements StorableData {

	private final StorableFile file;
	private final byte[] bytes;

	public StorageFileData(StorableFile file, byte[] bytes) {
		this.file = file;
		this.bytes = bytes;
	}

	@Override
    public byte[] getBytes() {
		return this.bytes;
	}

	@Override
	public String getFilename() {
		return this.file.getFilename();
	}

	@Override
	public String getFilePath() {
		return this.file.getFilePath();
	}

	public StorableFile getFile() {
		return this.file;
	}

}
