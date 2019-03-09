package com.dereekb.gae.server.storage.exception;

/**
 * Exception for when the data cannot be read into the target format.
 * <p>
 * For example, if a {@link byte[]} object attempted to be read as an image, but
 * it isn't an image.
 * </p>
 *
 * @author dereekb
 *
 */
public class InvalidFileDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidFileDataException() {
		super();
	}

	public InvalidFileDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidFileDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidFileDataException(String message) {
		super(message);
	}

	public InvalidFileDataException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "NoFileDataException []";
	}

}
