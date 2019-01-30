package com.dereekb.gae.server.storage.exception;

/**
 * Exception for when no data is available on a specified file.
 * <p>
 * Is different from {@link MissingFileException}, since the file exists but
 * doesn't have any actual data associated with it by having a content size of 0
 * bytes.
 * </p>
 * <p>
 * Is generally only used for uploaded files.
 * </p>
 *
 * @author dereekb
 *
 */
public class NoFileDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoFileDataException() {
		super();
	}

	public NoFileDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoFileDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoFileDataException(String message) {
		super(message);
	}

	public NoFileDataException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "NoFileDataException []";
	}

}
