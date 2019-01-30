package com.dereekb.gae.server.auth.security.token.provider.details;

/**
 * Enum of different user types.
 * 
 * @author dereekb
 *
 */
public enum LoginTokenUserType {

	ANONYMOUS,

	NEW_USER,

	USER,

	/**
	 * System/Administrator type.
	 */
	ADMINISTRATOR;

}
