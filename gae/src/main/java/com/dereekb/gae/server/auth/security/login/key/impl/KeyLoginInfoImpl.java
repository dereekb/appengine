package com.dereekb.gae.server.auth.security.login.key.impl;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.googlecode.objectify.Key;

/**
 * {@link KeyLoginInfo} implementation.
 * 
 * @author dereekb
 *
 */
public class KeyLoginInfoImpl
        implements KeyLoginInfo {

	private static final ExtendedObjectifyModelKeyUtil<LoginKey> LOGIN_KEY_UTIL = ExtendedObjectifyModelKeyUtil
	        .make(LoginKey.class, ModelKeyType.NAME);

	private Key<LoginKey> key;
	private String verification;

	public KeyLoginInfoImpl(String key, String verification) throws IllegalArgumentException {
		this(LOGIN_KEY_UTIL.keyFromString(key), verification);
	}

	public KeyLoginInfoImpl(Key<LoginKey> key, String validation) throws IllegalArgumentException {
		this.setKey(key);
		this.setVerification(validation);
	}

	public Key<LoginKey> getKey() {
		return key;
	}

	public void setKey(Key<LoginKey> key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null.");
		}

		this.key = key;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) throws IllegalArgumentException {
		if (verification == null) {
			throw new IllegalArgumentException("Verification cannot be null.");
		}

		this.verification = verification;
	}

	@Override
	public String toString() {
		return "KeyLoginInfoImpl [key=" + key + ", verification=" + verification + "]";
	}

}
