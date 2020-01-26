package com.dereekb.gae.server.auth.model.pointer;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.IndexCoded;

/**
 * {@link LoginPointer} type.
 *
 * @author dereekb
 *
 */
public enum LoginPointerType implements IndexCoded {

    // Internal
	SYSTEM(0, LoginType.SYSTEM, "S", true),

	ANONYMOUS(1, LoginType.NONE, "A", true),

	API_KEY(2, LoginType.API, "K", true),

	REFRESH_TOKEN(3, LoginType.REFRESH, "R", true),

	@Deprecated
	REMOTE_SYSTEM(4, LoginType.SYSTEM, "X", true),

    // External
	PASSWORD(5, LoginType.PASSWORD, "P", false),

	OAUTH_GOOGLE(6, LoginType.OAUTH, "G", false),

	OAUTH_FACEBOOK(7, LoginType.OAUTH, "F", false),

	/**
	 * Sign In With Apple
	 */
	OAUTH_APPLE(8, LoginType.OAUTH, "A", false);

	/**
	 * Login type/category.
	 *
	 * @author dereekb
	 */
	public static enum LoginType {

		SYSTEM,

		@Deprecated
		REMOTE_SYSTEM,

		NONE,

		PASSWORD,

		REFRESH,

		API,

		OAUTH

	}

	public static final String LOGIN_POINTER_FORMAT = "%s_%s";

	public final int code;
	public final LoginType type;
	public final String prefix;
	public final boolean internal;

	private LoginPointerType(int code, LoginType type, String prefix, boolean internal) {
		this.code = code;
		this.type = type;
		this.prefix = prefix;
		this.internal = internal;
	}

	public int getId() {
		return this.code;
	}

	@Override
	public Integer getCode() {
		return this.code;
	}

	public LoginType getType() {
		return this.type;
	}

	public String getPrefix() {
		return this.prefix;
	}

	/**
	 * Whether or not this login pointer type is a special, internally generated
	 * type.
	 *
	 * @return {@code true} if internal pointer type.
	 */
	public boolean isInternalType() {
		return this.internal;
	}

	public ModelKey makeKey(String id) {
		String name = String.format(LOGIN_POINTER_FORMAT, this.prefix, id);
		return new ModelKey(name);
	}

	public static LoginPointerType valueOf(Integer id) {
		LoginPointerType type = LoginPointerType.ANONYMOUS;

		switch (id) {
			case 0:
				type = LoginPointerType.SYSTEM;
				break;
			case 1:
				type = LoginPointerType.ANONYMOUS;
				break;
			case 2:
				type = LoginPointerType.API_KEY;
				break;
			case 3:
				type = LoginPointerType.REFRESH_TOKEN;
				break;
			case 4:
				type = LoginPointerType.REMOTE_SYSTEM;
				break;
			case 5:
				type = LoginPointerType.PASSWORD;
				break;
			case 6:
				type = LoginPointerType.OAUTH_GOOGLE;
				break;
			case 7:
				type = LoginPointerType.OAUTH_FACEBOOK;
				break;
			case 8:
				type = LoginPointerType.OAUTH_APPLE;
				break;
		}

		return type;
	}

}
