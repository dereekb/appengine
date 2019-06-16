package com.dereekb.gae.server.storage.upload.handler.impl.delegate.impl;

import com.dereekb.gae.server.deprecated.storage.upload.handler.impl.delegate.UploadedFileResult;
import com.dereekb.gae.server.deprecated.storage.upload.reader.UploadedFile;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * {@link UploadedFileResult} implementation.
 *
 * @author dereekb
 *
 */
public class UploadedFileResultImpl
        implements UploadedFileResult {

	private UploadedFile uploadedFile;
	private ApiResponseData responseData;
	private boolean wasUsed;
	private String fileMessage;

	public UploadedFileResultImpl(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public UploadedFileResultImpl(UploadedFile uploadedFile,
	        ApiResponseData responseData,
	        boolean wasUsed) {
		this.uploadedFile = uploadedFile;
		this.responseData = responseData;
		this.wasUsed = wasUsed;
	}

	public UploadedFileResultImpl(UploadedFile uploadedFile,
	        ApiResponseData responseData,
	        boolean wasUsed,
	        String fileMessage) {
		this.uploadedFile = uploadedFile;
		this.responseData = responseData;
		this.wasUsed = wasUsed;
		this.fileMessage = fileMessage;
	}

	public static UploadedFileResult withException(UploadedFile file,
	                                               Exception e) {
		return withMessage(file, e.getMessage());
	}

	public static UploadedFileResultImpl withMessage(UploadedFile uploadedFile,
	                                                 String message) {
		return new UploadedFileResultImpl(uploadedFile, null, false, message);
	}

	@Override
	public UploadedFile getUploadedFile() {
		return this.uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	@Override
	public ApiResponseData getResponseData() {
		return this.responseData;
	}

	public void setResponseData(ApiResponseData responseData) {
		this.responseData = responseData;
	}

	public boolean isWasUsed() {
		return this.wasUsed;
	}

	public void setWasUsed(boolean wasUsed) {
		this.wasUsed = wasUsed;
	}

	@Override
	public boolean fileWasUsed() {
		return this.wasUsed;
	}

	@Override
	public String getFileMessage() {
		return this.fileMessage;
	}

	public void setFileMessage(String fileMessage) {
		this.fileMessage = fileMessage;
	}

	@Override
	public String toString() {
		return "UploadedFileResultImpl [uploadedFile=" + this.uploadedFile + ", responseData=" + this.responseData
		        + ", wasUsed=" + this.wasUsed + ", fileMessage=" + this.fileMessage + "]";
	}

}
