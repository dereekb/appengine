package com.dereekb.gae.server.auth.security.login.password.impl;

import com.dereekb.gae.server.auth.security.login.password.PasswordRestriction;
import com.dereekb.gae.server.auth.security.login.password.exception.PasswordRestrictionException;

/**
 * {@link PasswordRestriction} implementation that checks the min and max
 * lengths.
 *
 * @author dereekb
 *
 */
public class LengthPasswordRestriction
        implements PasswordRestriction {

	private static final Integer DEFAULT_MIN = 8;
	private static final Integer DEFAULT_MAX = 32;

	private Integer minLength = DEFAULT_MIN;
	private Integer maxLength = DEFAULT_MAX;

	public LengthPasswordRestriction() {
		this(DEFAULT_MIN, DEFAULT_MAX);
	}

	public LengthPasswordRestriction(Integer minLength, Integer maxLength) {
		super();
		this.setMinLength(minLength);
		this.setMaxLength(maxLength);
	}

	public Integer getMinLength() {
		return this.minLength;
	}

	public void setMinLength(Integer minLength) {
		if (minLength == null || minLength < 0) {
			throw new IllegalArgumentException("minLength cannot be null or negative.");
		}

		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return this.maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		if (maxLength == null || maxLength < 0) {
			throw new IllegalArgumentException("maxLength cannot be null or negative.");
		}

		this.maxLength = maxLength;
	}

	// MARK: PasswordRestriction
	@Override
	public void assertIsValidPassword(String password) throws PasswordRestrictionException {
		int length = password.length();

		if (length < this.minLength) {
			throw new PasswordRestrictionException("Password must have at least " + this.minLength + " characters.");
		} else if (length > this.maxLength) {
			throw new PasswordRestrictionException("Password must have at most " + this.maxLength + " characters.");
		}
	}

	@Override
	public String toString() {
		return "LengthPasswordRestriction [minLength=" + this.minLength + ", maxLength=" + this.maxLength + "]";
	}

}
