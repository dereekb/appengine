package com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload;

import java.util.Date;

import com.dereekb.gae.server.storage.upload.UploadedFile;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.FileInfo;

/**
 * Represents an uploaded blobfile and relevant information.
 * 
 * @author dereekb
 */
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public BlobKey getKey() {
		return key;
	}

	@Override
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public String toString() {
		return "UploadedBlobFile [key=" + key + ", contentType=" + contentType + ", filename=" + filename
		        + ", creationDate=" + creationDate + ", size=" + size + "]";
	}
}
