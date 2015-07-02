package com.dereekb.gae.server.storage.file;

public class StorageFileContent extends StorageFileData
        implements StorableContent {

	private final String contentType;
	private StorageFileOptions options;

	public StorageFileContent(StorableFile file, byte[] bytes, String contentType) {
		super(file, bytes);
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public StorageFileOptions getOptions() {
		return options;
	}

	public void setOptions(StorageFileOptions options) {
		this.options = options;
	}

}
