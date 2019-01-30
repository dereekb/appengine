package com.dereekb.gae.model.extension.data.storage.importer;

/**
 * Exception raised when importing fails.
 *
 * @author dereekb
 */
public class ImportException extends Exception {

	private static final long serialVersionUID = 1L;

	public ImportException(Throwable cause) {
		super(cause);
	}

	public ImportException(String message) {
		super(message);
	}

	public ImportException(String message, Throwable cause) {
		super(message, cause);
	}

}
