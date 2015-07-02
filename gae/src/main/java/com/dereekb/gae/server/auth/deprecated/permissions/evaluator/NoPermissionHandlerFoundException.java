package com.dereekb.gae.server.auth.deprecated.permissions.evaluator;

public final class NoPermissionHandlerFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoPermissionHandlerFoundException(String permissionsRequest) {
		super("Could not find handler for request: '" + permissionsRequest + "'.");
	}
	
}

