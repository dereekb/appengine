package com.dereekb.gae.server.auth.security.token.model;

/**
 * {@link LoginTokenBuilder} options.
 *
 * @author dereekb
 *
 */
public interface LoginTokenBuilderOptions {

	/**
	 * Whether or not refreshing with this token is allowed.
	 *
	 * @return {@code true} or {@code false}. How {@code null} is interpreteded
	 *         is up to the implementation.
	 */
	public Boolean getRefreshAllowed();

	/**
	 * Bit mask used to AND roles.
	 *
	 * @return {@code Long} or {@code null} if no mask should be applied.
	 */
	public Long getRolesMask();

}
