package com.thevisitcompany.gae.deprecated.web.exceptions;

public class ResourceUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceUnavailableException() {}

	public ResourceUnavailableException(String message) {
		super(message);
	}

}