package com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload;

import java.util.Date;

import com.dereekb.gae.server.storage.upload.deprecated.UploadedFile;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.FileInfo;

/**
 * Represents an uploaded blobfile and relevant information.
 *
 * @author dereekb
 */
@Deprecated
public class UploadedBlobFile
        implements UploadedFile {

	private final BlobKey key;
	private String contentType;
	private String filename;
	private Date creationDate;
	private byte[] bytes;
	private Long size;

	public UploadedBlobFile(BlobKey key, FileInfo info) {
		this.key = key;
		this.contentType = info.getContentType();
		this.filename = info.getFilename();
		this.creationDate = info.getCreation();
		this.size = info.getSize();
	}

	@Override
    public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
    public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
    public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
    public Long getFileSize() {
		return this.size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public BlobKey getKey() {
		return this.key;
	}

	@Override
	public byte[] getBytes() {
		return this.bytes;
	}

	@Override
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public String toString() {
		return "UploadedBlobFile [key=" + this.key + ", contentType=" + this.contentType + ", filename=" + this.filename
		        + ", creationDate=" + this.creationDate + ", size=" + this.size + "]";
	}
}
