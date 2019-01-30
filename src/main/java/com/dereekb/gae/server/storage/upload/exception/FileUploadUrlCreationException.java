package com.dereekb.gae.server.storage.upload.exception;

/**
 * Thrown by {@link FileUploadUrlFactory} when a URL fails to be generated.
 *
 * @author dereekb
 *
 */
public class FileUploadUrlCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileUploadUrlCreationException() {
		super();
	}

	public FileUploadUrlCreationException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileUploadUrlCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileUploadUrlCreationException(String message) {
		super(message);
	}

	public FileUploadUrlCreationException(Throwable cause) {
		super(cause);
	}

}
