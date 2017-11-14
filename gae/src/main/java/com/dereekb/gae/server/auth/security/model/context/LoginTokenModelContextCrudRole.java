package com.dereekb.gae.server.auth.security.model.context;

/**
 * Default {@link LoginTokenModelContextRole} roles.
 * <p>
 * Grants basic CRUD roles.
 * 
 * @author dereekb
 *
 */
public enum LoginTokenModelContextCrudRole implements LoginTokenModelContextRole {

	READ(0, "read"),

	UPDATE(1, "update"),

	DELETE(2, "delete"),

	CREATE(3, "create"),
	
	SEARCH(4, "search");

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

	@Override
	public String getAuthority() {
		return this.role;
	}

}
