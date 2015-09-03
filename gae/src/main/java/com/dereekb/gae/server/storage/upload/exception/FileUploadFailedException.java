package com.dereekb.gae.server.storage.upload.exception;


public class FileUploadFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileUploadFailedException() {
		super();
	}

	public FileUploadFailedException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileUploadFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileUploadFailedException(String message) {
		super(message);
	}

	public FileUploadFailedException(Throwable cause) {
		super(cause);
	}

}
