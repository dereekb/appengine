package com.dereekb.gae.server.storage.services.gcs.impl;

import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.services.gcs.object.request.GcsStorableFileRequest;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;

/**
 * Wraps a {@link StorableFile} and defines a GCS Bucket to use and
 * {@link GcsFileOptions} options.
 *
 * @author dereekb
 *
 */
public class GcsStorableFileRequestImpl
        implements GcsStorableFileRequest {

	private String gcsBucket;
	private StorableFile file;

	public GcsStorableFileRequestImpl(String gcsBucket, StorableFile file) {
		this.setGcsBucket(gcsBucket);
		this.setFile(file);
	}

	public String getGcsBucket() {
		return this.gcsBucket;
	}

	public void setGcsBucket(String gcsBucket) {
		this.gcsBucket = gcsBucket;
	}

	public StorableFile getFile() {
		return this.file;
	}

	public void setFile(StorableFile file) {
		this.file = file;
	}

	// MARK: GcsStorableFile
	@Override
	public GcsFilename getGcsFilename() {
		String path = this.file.getFilePath();
		GcsFilename filename = new GcsFilename(this.gcsBucket, path);
		return filename;
	}

	@Override
	public String toString() {
		return "GcsStorableFileRequestImpl [gcsBucket=" + this.gcsBucket + ", file=" + this.file + "]";
	}

}
