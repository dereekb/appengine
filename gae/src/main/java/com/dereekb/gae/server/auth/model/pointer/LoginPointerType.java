package com.dereekb.gae.server.auth.model.pointer;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LoginPointer} type.
 *
 * @author dereekb
 *
 */
public enum LoginPointerType {

	PASSWORD(0, LoginType.PASSWORD, "P"),

	API_KEY(1, LoginType.API_KEY, "K"),

	OAUTH_GOOGLE(2, LoginType.OAUTH, "G"),

	OAUTH_FACEBOOK(3, LoginType.OAUTH, "F");

	/**
	 * Login type/category.
	 *
	 * @author dereekb
	 */
	public static enum LoginType {
		
		PASSWORD,
		
		API_KEY,

		OAUTH

	}

	public static final String LOGIN_POINTER_FORMAT = "%s%s";

	public final int id;
	public final LoginType type;
	public final String prefix;

	private LoginPointerType(int id, LoginType type, String prefix) {
		this.id = id;
		this.type = type;
		this.prefix = prefix;
	}

	public int getId() {
		return this.id;
	}

	public LoginType getType() {
		return this.type;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public ModelKey makeKey(String id) {
		String name = String.format(LOGIN_POINTER_FORMAT, this.prefix, id);
		return new ModelKey(name);
	}

	public static LoginPointerType valueOf(Integer id) {
		LoginPointerType type = LoginPointerType.PASSWORD;

		switch (id) {
			case 0:
				type = LoginPointerType.PASSWORD;
				break;
			case 1:
				type = LoginPointerType.API_KEY;
				break;
			case 2:
				type = LoginPointerType.OAUTH_GOOGLE;
				break;
			case 3:
				type = LoginPointerType.OAUTH_FACEBOOK;
				break;
		}

		return type;
	}

}
