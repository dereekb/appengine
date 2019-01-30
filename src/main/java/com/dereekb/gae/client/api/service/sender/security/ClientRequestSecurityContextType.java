package com.dereekb.gae.client.api.service.sender.security;

/**
 * Security context types.
 *
 * @author dereekb
 *
 */
public enum ClientRequestSecurityContextType {

	/**
	 * A system token should be used in the request.
	 */
	SYSTEM,

	/**
	 * The current security token should be passed onto the request.
	 */
	CURRENT,

	/**
	 * Use an override token.
	 */
	OVERRIDE,

	/**
	 * No security should be attached to the request.
	 */
	NONE

}
