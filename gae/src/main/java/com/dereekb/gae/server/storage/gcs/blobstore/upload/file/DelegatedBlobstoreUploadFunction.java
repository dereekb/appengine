package com.dereekb.gae.server.storage.gcs.blobstore.upload.file;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.storage.gcs.blobstore.upload.BlobstoreUploadHandlerFunction;
import com.dereekb.gae.server.storage.gcs.blobstore.upload.BlobstoreUploadedBlobReader;
import com.dereekb.gae.server.storage.gcs.blobstore.upload.UploadedBlobFile;

/**
 * A basic class that uses a delegate to filter and handle the uploaded files.
 * 
 * @author dereekb
 *
 */
public class DelegatedBlobstoreUploadFunction<T>
        implements BlobstoreUploadHandlerFunction<T> {

	private DelegatedBlobstoreUploadDelegate<T> delegate;
	private BlobstoreUploadedBlobReader uploadedBlobReader;

	@Override
	public List<T> runWithUploadedFiles(List<UploadedBlobFile> files) {

		List<UploadedBlobFile> filteredFiles = new ArrayList<UploadedBlobFile>();

		// Load Files
		if (uploadedBlobReader != null) {
			for (UploadedBlobFile file : files) {
				if (delegate.shouldLoadFile(file)) {
					byte[] bytes = uploadedBlobReader.readBytes(file);
					file.setBytes(bytes);
				}
			}
		}

		// Filter uploaded
		for (UploadedBlobFile file : files) {
			boolean isValid = delegate.isValidFile(file);

			if (isValid) {
				filteredFiles.add(file);
			}
		}

		// Handle data
		List<T> results = new ArrayList<T>();

		for (UploadedBlobFile file : filteredFiles) {
			T result = this.delegate.useFile(file);
			results.add(result);
		}

		return results;
	}

	public DelegatedBlobstoreUploadDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(DelegatedBlobstoreUploadDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public BlobstoreUploadedBlobReader getUploadedBlobReader() {
		return uploadedBlobReader;
	}

	public void setUploadedBlobReader(BlobstoreUploadedBlobReader uploadedBlobReader) {
		this.uploadedBlobReader = uploadedBlobReader;
	}

}
