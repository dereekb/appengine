package com.dereekb.gae.server.auth.model.login.security.requestor;

/**
 * Types of requester.
 *
 * @author dereekb
 *
 */
public enum RequesterContextType {

	/**
	 * Requester is the same.
	 */
	SELF,

	/**
	 * Requester is making the request on behalf of another.
	 */
	OTHER

}
