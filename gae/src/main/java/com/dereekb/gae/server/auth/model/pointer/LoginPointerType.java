package com.dereekb.gae.server.auth.model.pointer;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LoginPointer} type.
 *
 * @author dereekb
 *
 */
public enum LoginPointerType {
	
	ANONYMOUS(0, LoginType.NONE, "A"),

	PASSWORD(1, LoginType.PASSWORD, "P"),

	API_KEY(2, LoginType.API, "K"),

	OAUTH_GOOGLE(3, LoginType.OAUTH, "G"),

	OAUTH_FACEBOOK(4, LoginType.OAUTH, "F");

	/**
	 * Login type/category.
	 *
	 * @author dereekb
	 */
	public static enum LoginType {
		
		NONE,
		
		PASSWORD,
		
		API,

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
				type = LoginPointerType.ANONYMOUS;
				break;
			case 1:
				type = LoginPointerType.PASSWORD;
				break;
			case 2:
				type = LoginPointerType.API_KEY;
				break;
			case 3:
				type = LoginPointerType.OAUTH_GOOGLE;
				break;
			case 4:
				type = LoginPointerType.OAUTH_FACEBOOK;
				break;
		}

		return type;
	}

}
