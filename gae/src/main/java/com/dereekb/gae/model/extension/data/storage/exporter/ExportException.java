package com.dereekb.gae.model.extension.data.storage.exporter;

/**
 * Exception raised when exporting fails.
 *
 * @author dereekb
 */
public class ExportException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExportException(Throwable cause) {
		super(cause);
	}

	public ExportException(String message) {
		super(message);
	}

	public ExportException(String message, Throwable cause) {
		super(message, cause);
	}

}
