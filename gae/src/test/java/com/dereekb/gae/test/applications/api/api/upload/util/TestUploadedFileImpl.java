package com.dereekb.gae.test.applications.api.api.upload.util;

import java.io.IOException;
import java.util.Date;

import com.dereekb.gae.server.storage.exception.NoFileDataException;
import com.dereekb.gae.server.storage.upload.reader.UploadedFile;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileInfo;

public class TestUploadedFileImpl
        implements UploadedFile, UploadedFileInfo {

	private byte[] bytes;
	private String filename;
	private String contentType;

	public TestUploadedFileImpl(byte[] bytes, String filename, String contentType) {
		this.bytes = bytes;
		this.filename = filename;
		this.contentType = contentType;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	// MARK: UploadedFile
	@Override
	public byte[] getUploadedFileBytes() throws IOException, NoFileDataException {
		return this.bytes;
	}

	@Override
	public UploadedFileInfo getUploadedFileInfo() {
		return this;
	}

	// MARK: UploadedFileInfo
	@Override
	public String getFilename() {
		return this.filename;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public Date getCreationDate() {
		return new Date();
	}

	@Override
	public Long getFileSize() {
		return new Long(this.bytes.length);
	}

	@Override
	public String getMd5Hash() {
		return "HASH";
	}

}
