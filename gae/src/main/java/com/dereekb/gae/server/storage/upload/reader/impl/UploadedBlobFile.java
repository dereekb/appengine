package com.dereekb.gae.server.storage.upload.reader.impl;

import java.io.IOException;

import com.dereekb.gae.server.storage.upload.reader.UploadedFile;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileInfo;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.FileInfo;

/**
 * {@link UploadedFile} implementation using the Blobstore.
 *
 * @author dereekb
 *
 */
public class UploadedBlobFile
        implements UploadedFile, UploadedFileInfo {

	private final UploadedBlobFileDelegate delegate;
	private final BlobKey blobKey;
	private final FileInfo fileInfo;

	public UploadedBlobFile(UploadedBlobFileDelegate delegate, BlobKey blobKey, FileInfo fileInfo) {
		this.delegate = delegate;
		this.blobKey = blobKey;
		this.fileInfo = fileInfo;
	}

	public BlobKey getBlobKey() {
		return this.blobKey;
	}

	public FileInfo getFileInfo() {
		return this.fileInfo;
	}

	/**
	 * Convenience function for retrieving the size of the file using
	 * {@link #getFileInfo}.
	 */
	public Long getSize() {
		return this.fileInfo.getSize();
	}

	// MARK: UploadedFile
	@Override
	public byte[] getUploadedFileBytes() throws IOException {
		return this.delegate.readFileBytes(this);
	}

	@Override
	public UploadedFileInfo getUploadedFileInfo() {
		return this;
	}

}
