package com.dereekb.gae.server.auth.security.model.roles.impl;

import com.dereekb.gae.server.auth.security.model.roles.IndexCodedModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRole;

/**
 * Default {@link ModelRole} roles.
 * <p>
 * Grants basic CRUD roles for a specific model.
 * <p>
 * Occupies the role index set from 0-9.
 *
 * @author dereekb
 * @see ChildCrudModelRole
 */
public enum CrudModelRole implements IndexCodedModelRole {

	/**
	 * Is the actual owner of the model.
	 */
	OWNED(0, "crud_owner"),

	/**
	 * Read the model.
	 */
	READ(1, "crud_read"),

	/**
	 * Update the model.
	 */
	UPDATE(2, "crud_update"),

	/**
	 * Delete the model.
	 */
	DELETE(3, "crud_delete"),

	/**
	 * Search the model or indexes based around this model.
	 */
	SEARCH(4, "crud_search"),

	/**
	 * Change the model's links.
	 */
	LINK(5, "crud_link");

	public final int code;
	public final String role;

	private CrudModelRole(final int code, final String role) {
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
