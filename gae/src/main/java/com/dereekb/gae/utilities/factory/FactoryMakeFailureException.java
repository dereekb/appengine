package com.dereekb.gae.utilities.factory;

/**
 * Runtime exception thrown when a {@link Factory} cannot complete the make() function.
 * 
 * @author dereekb
 */
public class FactoryMakeFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FactoryMakeFailureException() {}

	public FactoryMakeFailureException(String string) {
		super(string);
	}
}
