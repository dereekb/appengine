package com.dereekb.gae.server.auth.security.login.password.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.login.password.PasswordRestriction;
import com.dereekb.gae.server.auth.security.login.password.exception.PasswordRestrictionException;

/**
 * Contains a set of {@link PasswordRestriction} and uses each one to assert
 * validity.
 *
 * @author dereekb
 *
 */
public class MultiPasswordRestriction
        implements PasswordRestriction {

	private List<PasswordRestriction> restrictions;

	public List<PasswordRestriction> getRestrictions() {
		return this.restrictions;
	}

	public void setRestrictions(List<PasswordRestriction> restrictions) {
		if (restrictions == null) {
			throw new IllegalArgumentException("restrictions cannot be null.");
		}

		this.restrictions = restrictions;
	}

	// MARK: PasswordRestriction
	@Override
	public void assertIsValidPassword(String password) throws PasswordRestrictionException {
		for (PasswordRestriction restriction : this.restrictions) {
			restriction.assertIsValidPassword(password);
		}
	}

	@Override
	public String toString() {
		return "MultiPasswordRestriction [restrictions=" + this.restrictions + "]";
	}

}
