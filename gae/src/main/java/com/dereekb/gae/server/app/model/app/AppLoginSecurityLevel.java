package com.dereekb.gae.server.app.model.app;

import com.dereekb.gae.utilities.misc.keyed.IndexCoded;
import com.dereekb.gae.utilities.misc.keyed.exception.UnknownIndexCodeException;

/**
 * Level of security for a specific app.
 *
 * @author dereekb
 *
 */
public enum AppLoginSecurityLevel implements IndexCoded {

	/**
	 * Basic application.
	 * <p>
	 * Can make requests on others behalf.
	 */
	APP(0, false),

	/**
	 * System application.
	 * <p>
	 * Can make all types of requests.
	 */
	SYSTEM(1, true);

	public final int code;
	public final boolean admin;

	private AppLoginSecurityLevel(int code, boolean admin) {
		this.code = code;
		this.admin = admin;
	}

	@Override
	public Integer getCode() {
		return this.code;
	}

	public boolean isAdmin() {
		return this.admin;
	}

	public static AppLoginSecurityLevel valueOf(int code) throws UnknownIndexCodeException {
		switch (code) {
			case 0:
				return AppLoginSecurityLevel.APP;
			case 1:
				return AppLoginSecurityLevel.SYSTEM;
			default:
				throw new UnknownIndexCodeException();
		}
	}

}
