package com.dereekb.gae.server.storage.upload.handler.impl;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.storage.upload.exception.FileUploadFailedException;
import com.dereekb.gae.server.storage.upload.handler.FileUploadHandler;
import com.dereekb.gae.server.storage.upload.handler.FileUploadHandlerResult;
import com.dereekb.gae.server.storage.upload.handler.impl.delegate.FileUploadHandlerDelegate;
import com.dereekb.gae.server.storage.upload.handler.impl.delegate.FileUploadHandlerDelegateResult;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileReader;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileSet;
import com.dereekb.gae.utilities.collections.map.catchmap.CatchMap;

/**
 * {@link FileUploadHandler} implementation.
 *
 * Uses a {@link FileUploadHandlerDelegate} to perform actual creation, and
 * deletes the uploaded files.
 *
 * @author dereekb
 *
 */
public class FileUploadHandlerImpl
        implements FileUploadHandler {

	private CatchMap<String, FileUploadHandlerDelegate> delegates;
	private UploadedFileReader reader;

	public FileUploadHandlerImpl(CatchMap<String, FileUploadHandlerDelegate> delegates, UploadedFileReader reader) {
		this.delegates = delegates;
		this.reader = reader;
	}

	public CatchMap<String, FileUploadHandlerDelegate> getDelegates() {
		return this.delegates;
	}

	public void setDelegates(CatchMap<String, FileUploadHandlerDelegate> delegates) {
		this.delegates = delegates;
	}

	public UploadedFileReader getReader() {
		return this.reader;
	}

	public void setReader(UploadedFileReader reader) {
		this.reader = reader;
	}

	// MARK: FileUploadHandler
	@Override
	public FileUploadHandlerResult handleUploadRequest(String type,
	                                                   HttpServletRequest request) throws FileUploadFailedException {
		FileUploadHandlerResult result = null;
		UploadedFileSet set = this.reader.readUploadedFiles(request);

		try {
			FileUploadHandlerDelegate delegate = this.delegates.get(type);

			if (delegate == null) {
				throw new RuntimeException("No delegate available for type: '" + type + "'.");
			}

			FileUploadHandlerDelegateResult delegateResult = delegate.handleUploadedFiles(set);
			result = this.buildResult(delegateResult);
		} catch (Exception e) {
			throw new FileUploadFailedException(e);
		} finally {
			// Cleanup the uploaded data.
			set.deleteSetData();
		}

		return result;
	}

	private FileUploadHandlerResult buildResult(FileUploadHandlerDelegateResult delegateResult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "FileUploadHandlerImpl [delegates=" + this.delegates + ", reader=" + this.reader + "]";
	}

}
