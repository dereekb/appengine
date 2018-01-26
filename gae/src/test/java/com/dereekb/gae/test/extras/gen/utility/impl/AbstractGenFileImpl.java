package com.dereekb.gae.test.extras.gen.utility.impl;

import com.dereekb.gae.test.extras.gen.utility.GenFile;

/**
 * Abstract {@link GenFile} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractGenFileImpl
        implements GenFile {

	private String fileName;
	private String fileType;

	public AbstractGenFileImpl(String fileName, String fileType) {
		super();
		this.setFileName(fileName);
		this.setFileType(fileType);
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("fileName cannot be null.");
		}

		this.fileName = fileName;
	}

	@Override
	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		if (fileType == null) {
			throw new IllegalArgumentException("fileType cannot be null.");
		}

		this.fileType = fileType;
	}

	@Override
	public String getOutputFileName() {
		return this.fileName + "." + this.fileType;
	}

	@Override
	public String toString() {
		return "AbstractGenFileImpl [fileName=" + this.fileName + ", fileType=" + this.fileType + "]";
	}

}
