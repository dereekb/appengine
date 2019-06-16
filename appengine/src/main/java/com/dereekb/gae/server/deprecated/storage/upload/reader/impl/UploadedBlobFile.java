package com.dereekb.gae.server.storage.upload.reader.impl;

import java.io.IOException;
import java.util.Date;

import com.dereekb.gae.server.deprecated.storage.upload.reader.UploadedFile;
import com.dereekb.gae.server.deprecated.storage.upload.reader.UploadedFileInfo;
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

	public UploadedBlobFileDelegate getDelegate() {
		return this.delegate;
	}

	public BlobKey getBlobKey() {
		return this.blobKey;
	}

	public FileInfo getFileInfo() {
		return this.fileInfo;
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

	// MARK: UploadedFileInfo
	@Override
	public String getFilename() {
		return this.fileInfo.getFilename();
	}

	@Override
	public String getContentType() {
		return this.fileInfo.getContentType();
	}

	@Override
	public Long getFileSize() {
		return this.fileInfo.getSize();
	}

	@Override
	public Date getCreationDate() {
		return this.fileInfo.getCreation();
	}

	@Override
	public String getMd5Hash() {
		return this.fileInfo.getMd5Hash();
	}

	@Override
	public String toString() {
		return "UploadedBlobFile [delegate=" + this.delegate + ", blobKey=" + this.blobKey + ", fileInfo="
		        + this.fileInfo + "]";
	}

}
