package com.dereekb.gae.server.auth.security.model.roles.impl;

import com.dereekb.gae.server.auth.security.model.roles.IndexCodedModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRole;

/**
 * Default {@link ModelRole} roles.
 * <p>
 * CRUD roles granted to children of an object.
 * <p>
 * Occupies the role index set from 10-19
 *
 * @author dereekb
 *
 */
public enum ChildCrudModelRole implements IndexCodedModelRole {

	/**
	 * Create any child models.
	 */
	CHILD_CREATE(10, "c_crud_create"),

	/**
	 * Read any child models.
	 */
	CHILD_READ(11, "c_crud_read"),

	/**
	 * Update any child models.
	 */
	CHILD_UPDATE(12, "c_crud_update"),

	/**
	 * Delete any child models.
	 */
	CHILD_DELETE(13, "c_crud_delete"),

	/**
	 * Can search based on this model.
	 */
	CHILD_SEARCH(14, "c_crud_search"),

	/**
	 * Change child model links.
	 */
	CHILD_LINK(15, "c_crud_link");

	public final int code;
	public final String role;

	private ChildCrudModelRole(int code, String role) {
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
