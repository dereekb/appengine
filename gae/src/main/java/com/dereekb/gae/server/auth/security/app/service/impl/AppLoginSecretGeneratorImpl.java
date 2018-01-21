package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.auth.security.app.service.AppLoginSecretGenerator;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link AppLoginSecretGenerator} implementation.
 *
 * @author dereekb
 *
 */
public class AppLoginSecretGeneratorImpl
        implements AppLoginSecretGenerator {

	public static Integer DEFAULT_LENGTH = 32;

	private Integer length;

	public AppLoginSecretGeneratorImpl() {
		this(DEFAULT_LENGTH);
	}

	public AppLoginSecretGeneratorImpl(Integer length) {
		this.setLength(length);
	}

	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		if (length == null) {
			throw new IllegalArgumentException("length cannot be null.");
		}

		this.length = length;
	}

	// MARK: AppLoginSecretGenerator
	@Override
	public String generateSecret() {
		return StringUtility.generateSecureRandomHexString(this.length);
	}

	@Override
	public String toString() {
		return "AppLoginSecretGeneratorImpl [length=" + this.length + "]";
	}

}
