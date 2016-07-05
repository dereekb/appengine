package com.dereekb.gae.server.auth.model.pointer;


/**
 * {@link LoginPointer} type.
 *
 * @author dereekb
 *
 */
public enum LoginPointerType {

	PASSWORD(0, LoginType.PASSWORD),

	OAUTH_GOOGLE(1, LoginType.OAUTH),

	OAUTH_FACEBOOK(2, LoginType.OAUTH);

	/**
	 * Login type/category.
	 *
	 * @author dereekb
	 */
	public static enum LoginType {

		PASSWORD,

		OAUTH

	}

	public final int id;
	public final LoginType type;

	private LoginPointerType(int id, LoginType type) {
		this.id = id;
		this.type = type;
	}

	public int getId() {
		return this.id;
	}

	public LoginType getType() {
		return this.type;
	}

	public static LoginPointerType valueOf(Integer id) {
		LoginPointerType type = LoginPointerType.PASSWORD;

		switch (id) {
			case 0:
				type = LoginPointerType.PASSWORD;
				break;
			case 1:
				type = LoginPointerType.OAUTH_GOOGLE;
				break;
			case 2:
				type = LoginPointerType.OAUTH_FACEBOOK;
				break;
		}

		return type;
	}

}
