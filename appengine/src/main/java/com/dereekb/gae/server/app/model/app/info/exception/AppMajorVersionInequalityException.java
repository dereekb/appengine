package com.dereekb.gae.server.app.model.app.info.exception;

/**
 * {@link AppInequalityException} for version inequalities.
 *
 * @author dereekb
 *
 */
public class AppMajorVersionInequalityException extends AppInequalityException {

	private static final long serialVersionUID = 1L;

	public AppMajorVersionInequalityException() {
		super("The major versions were different.");
	}

	public AppMajorVersionInequalityException(String message) {
		super(message);
	}

}
