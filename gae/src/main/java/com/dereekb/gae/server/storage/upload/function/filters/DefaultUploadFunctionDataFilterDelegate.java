package com.dereekb.gae.server.storage.upload.function.filters;

import com.dereekb.gae.server.storage.upload.UploadedFile;

/**
 * A default filter for uploaded files that can check whether or not the StorageFileInfo for that object matches.
 * 
 * @author dereekb
 *
 * @param <T>
 * @param <U>
 */
public class DefaultUploadFunctionDataFilterDelegate<U extends UploadedFile>
        implements UploadFunctionDataFilterDelegate<U> {

	private String filenameRegex;
	private String contentTypeRegex;
	private Long minSize;
	private Long maxSize;

	public boolean isValidData(U file) {
		boolean isValid = true;

		if (isValid && this.contentTypeRegex != null) {
			String contentType = file.getContentType();
			isValid = contentType.matches(this.contentTypeRegex);
		}

		if (isValid && this.minSize != null) {
			Long size = file.getSize();
			isValid = (size >= minSize);
		}

		if (isValid && this.maxSize != null) {
			Long size = file.getSize();
			isValid = (size <= maxSize);
		}

		if (isValid && this.filenameRegex != null) {
			String filename = file.getFilename();
			isValid = filename.matches(this.filenameRegex);
		}

		return isValid;
	}

	public String getContentTypeRegex() {
		return contentTypeRegex;
	}

	public void setContentTypeRegex(String contentTypeRegex) {
		this.contentTypeRegex = contentTypeRegex;
	}

	public String getFilenameRegex() {
		return filenameRegex;
	}

	public void setFilenameRegex(String filenameRegex) {
		this.filenameRegex = filenameRegex;
	}

	public Long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
	}

	public Long getMinSize() {
		return minSize;
	}

	public void setMinSize(Long minSize) {
		this.minSize = minSize;
	}

}
