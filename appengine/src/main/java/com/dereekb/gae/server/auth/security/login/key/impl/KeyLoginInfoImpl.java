package com.dereekb.gae.server.auth.security.login.key.impl;

import com.dereekb.gae.server.auth.security.login.key.KeyLoginInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;

/**
 * {@link KeyLoginInfo} implementation.
 * 
 * @author dereekb
 *
 */
public class KeyLoginInfoImpl
        implements KeyLoginInfo {

	private ModelKey key;
	private String verification;

	public KeyLoginInfoImpl(String key, String verification) throws IllegalArgumentException {
		this(StringLongModelKeyConverterImpl.CONVERTER.convertSingle(key), verification);
	}

	public KeyLoginInfoImpl(ModelKey key, String validation) throws IllegalArgumentException {
		this.setKey(key);
		this.setVerification(validation);
	}

	public ModelKey getKey() {
		return key;
	}

	public void setKey(ModelKey key) throws IllegalArgumentException {
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
