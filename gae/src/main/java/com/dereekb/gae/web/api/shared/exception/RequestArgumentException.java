package com.dereekb.gae.web.api.shared.exception;

/**
 * Thrown when an argument is unusable.
 *
 * Generally used for other internal-argument validation situations.
 *
 * @author dereekb
 */
public final class RequestArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Argument of the input request.
	 */
	private final String argument;

	/**
	 * Detailed reason.
	 */
	private final String detail;

	public RequestArgumentException(String argument, String detail) {
		this.argument = argument;
		this.detail = detail;
	}

	public String getArgument() {
		return this.argument;
	}

	public String getDetail() {
		return this.detail;
	}

}
