package com.dereekb.gae.server.auth.security.login.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.LoginPointerKeyFormatter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link LoginPointerKeyFormatter} implementation.
 *
 * @author dereekb
 *
 */
public class LoginPointerKeyFormatterImpl
        implements LoginPointerKeyFormatter {

	private String format;

	public LoginPointerKeyFormatterImpl(LoginPointerType type) {
		this.setFormat(type);
	}

	public LoginPointerKeyFormatterImpl(String format) {
		this.setFormat(format);
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(LoginPointerType type) {
		this.setFormat(type.getPrefix() + "%s");
	}

	public void setFormat(String format) {
		if (StringUtility.isEmptyString(format)) {
			throw new IllegalArgumentException("format cannot be null.");
		}

		this.format = format;
	}

	// MARK: LoginPointerKeyFormatter
	@Override
	public ModelKey getKeyForUsername(String username) {
		String id = this.getIdForUsername(username);
		return new ModelKey(id);
	}

	@Override
	public String getIdForUsername(String username) {
		return String.format(this.format, username.toLowerCase());
	}

	@Override
	public String toString() {
		return "LoginPointerKeyFormatterImpl [format=" + this.format + "]";
	}

}
