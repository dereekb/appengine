package com.thevisitcompany.gae.deprecated.web.exceptions;


public class UnsupportedRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsupportedRequestException() {
		super();
	}

	public UnsupportedRequestException(String message) {
		super(message);
	}

}