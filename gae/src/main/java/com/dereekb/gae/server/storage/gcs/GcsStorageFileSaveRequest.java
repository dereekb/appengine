package com.dereekb.gae.server.storage.gcs;

import com.dereekb.gae.server.storage.file.StorableContent;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;

/**
 * Wrapper/Extension of StorageFileRequest that wraps StorageFileData and contains the bucket and optional GcsOptions.
 * 
 * @author dereekb
 */
public class GcsStorageFileSaveRequest extends GcsStorageFileRequest {

	private final StorableContent file;

	public GcsStorageFileSaveRequest(String bucket, StorableContent file) {
		super(bucket, file);

		if (file == null) {
			throw new IllegalArgumentException("Cannot have a null file.");
		}

		this.file = file;
	}

	@Override
	public GcsFileOptions getOptions() {
		GcsFileOptions options = this.options;

		if (options == null) {
			GcsFileOptions.Builder builder = new GcsFileOptions.Builder();

			String mimeType = file.getContentType();
			builder = builder.mimeType(mimeType);
			options = builder.build();
		}

		return options;
	}

	public byte[] getData() {
		return file.getBytes();
	}

	public StorableContent getFile() {
		return file;
	}

}
