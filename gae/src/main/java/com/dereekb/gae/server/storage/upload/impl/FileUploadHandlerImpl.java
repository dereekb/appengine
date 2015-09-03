package com.dereekb.gae.server.storage.upload.impl;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.storage.upload.FileUploadHandler;
import com.dereekb.gae.server.storage.upload.FileUploadHandlerResult;
import com.dereekb.gae.server.storage.upload.exception.FileUploadFailedException;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileReader;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileSet;

/**
 * {@link FileUploadHandler} implementation.
 *
 * Uses a {@link FileUploadHandlerImplDelegate} to perform actual creation, and
 * deletes the uploaded files.
 *
 * @author dereekb
 *
 */
public class FileUploadHandlerImpl
        implements FileUploadHandler {

	private FileUploadHandlerImplDelegate delegate;
	private UploadedFileReader reader;

	public FileUploadHandlerImpl(FileUploadHandlerImplDelegate delegate, UploadedFileReader reader) {
		this.delegate = delegate;
		this.reader = reader;
	}

	public FileUploadHandlerImplDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(FileUploadHandlerImplDelegate delegate) {
		this.delegate = delegate;
	}

	public UploadedFileReader getReader() {
		return this.reader;
	}

	public void setReader(UploadedFileReader reader) {
		this.reader = reader;
	}

	// MARK: FileUploadHandler
	@Override
	public FileUploadHandlerResult handleUploadRequest(HttpServletRequest request) throws FileUploadFailedException {
		UploadedFileSet set = this.reader.readUploadedFiles(request);
		FileUploadHandlerResult result = null;

		try {
			result = this.delegate.handleUploadedFiles(set);
		} catch (Exception e) {
			throw new FileUploadFailedException(e);
		} finally {
			// Cleanup the uploaded data.
			set.deleteSetData();
		}

		return result;
	}

	@Override
	public String toString() {
		return "FileUploadHandlerImpl [delegate=" + this.delegate + ", reader=" + this.reader + "]";
	}

}
