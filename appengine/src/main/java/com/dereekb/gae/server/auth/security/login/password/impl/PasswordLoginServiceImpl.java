package com.dereekb.gae.server.auth.security.login.password.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.LoginPointerService;
import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginDisabledException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.impl.LoginServiceImpl;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginService;
import com.dereekb.gae.server.auth.security.login.password.PasswordRestriction;
import com.dereekb.gae.server.auth.security.login.password.exception.PasswordRestrictionException;

/**
 * {@link PasswordLoginService} implementation.
 *
 * @author dereekb
 *
 */
public class PasswordLoginServiceImpl extends LoginServiceImpl
        implements PasswordLoginService {

	private PasswordEncoder encoder;
	private PasswordRestriction restriction = new LengthPasswordRestriction();

	public PasswordLoginServiceImpl(LoginPointerService pointerService) throws IllegalArgumentException {
		this(new BCryptPasswordEncoder(), pointerService);
	}

	public PasswordLoginServiceImpl(PasswordEncoder encoder, LoginPointerService pointerService)
	        throws IllegalArgumentException {
		super(LoginPointerType.PASSWORD, pointerService);
		this.setEncoder(encoder);
	}

	public PasswordEncoder getEncoder() {
		return this.encoder;
	}

	public void setEncoder(PasswordEncoder encoder) throws IllegalArgumentException {
		if (encoder == null) {
			throw new IllegalArgumentException("Encoder cannot be null.");
		}

		this.encoder = encoder;
	}

	public PasswordRestriction getRestriction() {
		return this.restriction;
	}

	public void setRestriction(PasswordRestriction restriction) {
		if (restriction == null) {
			throw new IllegalArgumentException("restriction cannot be null.");
		}

		this.restriction = restriction;
	}

	// MARK: PasswordLoginService
	@Override
	public LoginPointer login(PasswordLoginPair pair)
	        throws LoginDisabledException,
	            LoginUnavailableException,
	            InvalidLoginCredentialsException {

		String username = pair.getUsername();
		String password = pair.getPassword();

		LoginPointer loginPointer = this.getLogin(username);

		String encodedPassword = loginPointer.getPassword();

		if (encodedPassword == null) {
			encodedPassword = "";
		}

		boolean match = this.encoder.matches(password, encodedPassword);

		if (!match) {
			throw new InvalidLoginCredentialsException(username);
		}

		return loginPointer;
	}

	@Override
	public LoginPointer create(PasswordLoginPair pair) throws PasswordRestrictionException, LoginExistsException {
		return this.create(pair, false);
	}

	@Override
	public LoginPointer create(PasswordLoginPair pair,
	                           boolean skipRestrictions)
	        throws LoginExistsException,
	            PasswordRestrictionException {
		String username = pair.getUsername();
		String password = pair.getPassword();

		if (skipRestrictions == false) {
			this.restriction.assertIsValidPassword(password);
		}

		LoginPointer loginPointer = new LoginPointer();

		String encodedPassword = this.encoder.encode(password);
		loginPointer.setPassword(encodedPassword);
		loginPointer.setLoginPointerType(LoginPointerType.PASSWORD);

		return this.createLogin(username, loginPointer);
	}

	@Override
	public String toString() {
		return "PasswordLoginServiceImpl [encoder=" + this.encoder + "]";
	}

}
