package com.dereekb.gae.server.app.model.app.info.exception;

/**
 * {@link AppInequalityException} for service inequalities.
 *
 * @author dereekb
 *
 */
public class AppIdInequalityException extends AppInequalityException {

	private static final long serialVersionUID = 1L;

	public AppIdInequalityException() {
		super("The app ids were different.");
	}

	public AppIdInequalityException(String message) {
		super(message);
	}

}
