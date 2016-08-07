package com.dereekb.gae.web.api.model.extension.upload.exception;


/**
 * Thrown when an upload type is unavailable or invalid.
 *
 * @author dereekb
 *
 */
public class InvalidUploadTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	private String type;

	public InvalidUploadTypeException(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
