package com.dereekb.gae.server.storage.gcs.files;

import com.dereekb.gae.server.storage.file.StorableContent;
import com.dereekb.gae.server.storage.file.StorableFile;
import com.dereekb.gae.server.storage.file.options.StorableFileOptionsImpl;
import com.google.appengine.tools.cloudstorage.GcsFileMetadata;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;

/**
 * Represents a file loaded from the GcsStorageAccessor;
 * 
 * @author dereekb
 */
public class GcsStorageFileData
        implements StorableContent {

	private final StorableFile file;
	private final GcsFilename gcsFilename;
	private final byte[] bytes;
	private final GcsFileMetadata metadata;
	private StorableFileOptionsImpl options;

	public GcsStorageFileData(StorableFile file, GcsFilename gcsFilename, byte[] bytes, GcsFileMetadata metadata) {
		this.file = file;
		this.gcsFilename = gcsFilename;
		this.bytes = bytes;
		this.metadata = metadata;
	}

	public byte[] getFileData() {
		return bytes;
	}

	@Override
	public String getFilename() {
		return file.getFilename();
	}

	public GcsFilename getGcsFilename() {
		return gcsFilename;
	}

	@Override
	public String getFilePath() {
		return file.getFilePath();
	}

	public StorableFile getFile() {
		return file;
	}

	public GcsFileMetadata getMetadata() {
		return metadata;
	}

	@Override
	public String getContentType() {
		GcsFileOptions options = this.metadata.getOptions();
		String contentType = ((options != null) ? options.getMimeType() : null);
		return contentType;
	}

	@Override
	public StorableFileOptionsImpl getOptions() {
		return this.options;
	}

	public void setOptions(StorableFileOptionsImpl options) {
		this.options = options;
	}
}
