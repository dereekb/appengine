package com.dereekb.gae.server.storage.gcs;

import com.dereekb.gae.server.storage.file.StorableContent;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;

/**
 * Wrapper/Extension of StorageFileRequest that wraps StorableDataImpl and contains the bucket and optional GcsOptions.
 *
 * @author dereekb
 */
@Deprecated
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

			String mimeType = this.file.getContentType();
			builder = builder.mimeType(mimeType);
			options = builder.build();
		}

		return options;
	}

	public byte[] getData() {
		return this.file.getFileData();
	}

	@Override
    public StorableContent getFile() {
		return this.file;
	}

}
