package com.dereekb.gae.server.storage.upload.handler.impl.delegate.impl;

import java.util.Collection;
import java.util.Collections;

import com.dereekb.gae.server.storage.upload.handler.impl.delegate.FileUploadHandlerDelegateResult;
import com.dereekb.gae.server.storage.upload.handler.impl.delegate.UploadedFileResult;

/**
 * {@link FileUploadHandlerDelegateResult} implementation.
 *
 * @author dereekb
 *
 */
public class FileUploadHandlerDelegateResultImpl
        implements FileUploadHandlerDelegateResult {

	private Collection<UploadedFileResult> usedFiles;
	private Collection<UploadedFileResult> failedFiles;

	public FileUploadHandlerDelegateResultImpl() {}

	public FileUploadHandlerDelegateResultImpl(Collection<UploadedFileResult> usedFiles,
	        Collection<UploadedFileResult> failedFiles) {
		this.setUsedFiles(usedFiles);
		this.setFailedFiles(failedFiles);
	}

	@Override
	public Collection<UploadedFileResult> getUsedFiles() {
		return this.usedFiles;
	}

	public void setUsedFiles(Collection<UploadedFileResult> usedFiles) {
		if (usedFiles == null) {
			usedFiles = Collections.emptyList();
		}

		this.usedFiles = usedFiles;
	}

	@Override
	public Collection<UploadedFileResult> getFailedFiles() {
		return this.failedFiles;
	}

	public void setFailedFiles(Collection<UploadedFileResult> failedFiles) {
		if (failedFiles == null) {
			failedFiles = Collections.emptyList();
		}

		this.failedFiles = failedFiles;
	}

	@Override
	public String toString() {
		return "FileUploadHandlerDelegateResultImpl [usedFiles=" + this.usedFiles + ", failedFiles=" + this.failedFiles
		        + "]";
	}

}
