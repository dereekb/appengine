package com.dereekb.gae.server.storage.gcs;

import com.dereekb.gae.server.storage.file.StorableFile;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;

/**
 * StorableFileImpl wrapper that includes GCS file options and the Gcs bucket name.
 * 
 * @author dereekb
 *
 */
public class GcsStorageFileRequest {

	protected GcsFileOptions options = null;
	private final String bucket;
	private final StorableFile file;

	public GcsStorageFileRequest(String bucket, StorableFile file) {
		this.bucket = bucket;
		this.file = file;
	}

	public GcsFilename getGcsFilename() {
		String path = file.getFilePath();
		GcsFilename filename = new GcsFilename(bucket, path);
		return filename;
	}

	public GcsFileOptions getOptions() {
		GcsFileOptions options = this.options;

		if (options == null) {
			options = GcsFileOptions.getDefaultInstance();
		}

		return options;
	}

	public void setOptions(GcsFileOptions options) {
		this.options = options;
	}

	public String getBucket() {
		return bucket;
	}

	public StorableFile getFile() {
		return file;
	}

}
