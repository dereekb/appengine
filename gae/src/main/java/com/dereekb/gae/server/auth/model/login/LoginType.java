package com.dereekb.gae.server.auth.model.login;

/**
 *
 * @author dereekb
 *
 */
public enum LoginType {

	/**
	 * Normal account.
	 */
	USER(0),

	/**
	 * Account with elevated privileges.
	 */
	MODERATOR(100),

	/**
	 * Account with full privileges.
	 */
	ADMIN(200);

	public final int id;

	private LoginType(int id) {
		this.id = id;
	}

}
