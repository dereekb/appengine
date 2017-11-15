package com.dereekb.gae.server.auth.security.model.context;

import com.dereekb.gae.utilities.misc.keyed.IndexCoded;

/**
 * Default {@link LoginTokenModelContextRole} roles.
 * <p>
 * Grants basic CRUD roles.
 * 
 * @author dereekb
 *
 */
public enum LoginTokenModelContextCrudRole implements IndexCoded, LoginTokenModelContextRole {

	/**
	 * Read related models.
	 */
	READ(0, "crud_read"),

	/**
	 * Update related models.
	 */
	UPDATE(1, "crud_update"),

	/**
	 * Delete related models.
	 */
	DELETE(2, "crud_delete"),

	/**
	 * Create related models.
	 */
	CREATE(3, "crud_create"),

	/**
	 * Search the model and related components.
	 */
	SEARCH(4, "crud_search"),

	/**
	 * Change model links.
	 */
	LINK(5, "crud_link");

	public final int code;
	public final String role;

	private LoginTokenModelContextCrudRole(int code, String role) {
		this.code = code;
		this.role = role;
	}

	@Override
	public Integer getCode() {
		return this.code;
	}

	@Override
	public String getRole() {
		return this.role;
	}

}
