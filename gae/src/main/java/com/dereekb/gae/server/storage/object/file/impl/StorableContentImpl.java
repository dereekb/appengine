package com.dereekb.gae.server.storage.object.file.impl;

import java.util.Arrays;

import com.dereekb.gae.server.storage.object.file.StorableContent;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.object.file.options.StorableFileOptionsImpl;

/**
 * {@code StorableContent} implementation.
 *
 * @author dereekb
 *
 */
public class StorableContentImpl extends StorableDataImpl
        implements StorableContent {

	private String contentType;
	private StorableFileOptionsImpl options;

	public StorableContentImpl(StorableFile file, byte[] bytes, String contentType) throws IllegalArgumentException {
		super(file, bytes);
		this.contentType = contentType;
	}

	public StorableContentImpl(StorableFile file, byte[] bytes, String contentType, StorableFileOptionsImpl options)
	        throws IllegalArgumentException {
		super(file, bytes);
		this.contentType = contentType;
		this.options = options;
	}

	@Override
    public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
    public StorableFileOptionsImpl getOptions() {
		return this.options;
	}

	public void setOptions(StorableFileOptionsImpl options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "StorableContentImpl [contentType=" + this.contentType + ", options=" + this.options
		        + ", getFilePath()=" + this.getFilePath() + ", getFilename()=" + this.getFilename()
		        + ", getFileData()=" + Arrays.toString(this.getFileData()) + "]";
	}

}
