package com.dereekb.gae.server.app.model.app.info.exception;

/**
 * {@link AppInequalityException} for service inequalities.
 *
 * @author dereekb
 *
 */
public class AppServiceInequalityException extends AppInequalityException {

	private static final long serialVersionUID = 1L;

	public AppServiceInequalityException() {
		super("The service names were different.");
	}

	public AppServiceInequalityException(String message) {
		super(message);
	}

}
