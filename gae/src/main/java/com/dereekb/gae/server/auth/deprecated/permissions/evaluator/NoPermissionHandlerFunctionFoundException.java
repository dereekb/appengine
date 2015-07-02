package com.dereekb.gae.server.auth.deprecated.permissions.evaluator;

/**
 * Thrown when no function is found for the PermissionsEvaluator to use for evaluating a PermissionEvent.
 * @author dereekb
 *
 */
public final class NoPermissionHandlerFunctionFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoPermissionHandlerFunctionFoundException(String function, PermissionsHandler handler) {
		super("Could not find handler function in class '" + handler.getClass() + "' for function: '" + function + "'");
	}
	
}
