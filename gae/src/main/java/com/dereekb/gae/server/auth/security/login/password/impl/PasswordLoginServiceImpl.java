package com.dereekb.gae.server.auth.security.login.password.impl;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.impl.LoginServiceImpl;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginService;
import com.dereekb.gae.server.datastore.GetterSetter;

/**
 * {@link PasswordLoginService} implementation.
 *
 * @author dereekb
 *
 */
public class PasswordLoginServiceImpl extends LoginServiceImpl
        implements PasswordLoginService {

	public static final String PASSWORD_PREFIX = "P";

	private static final String DEFAULT_FORMAT = PASSWORD_PREFIX + "%s";

	private PasswordEncoder encoder;

	public PasswordLoginServiceImpl(PasswordEncoder encoder, GetterSetter<LoginPointer> getterSetter)
	        throws IllegalArgumentException {
		super(DEFAULT_FORMAT, getterSetter);
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

	// MARK: PasswordLoginService
	@Override
	public LoginPointer login(PasswordLoginPair pair)
	        throws LoginUnavailableException,
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
	public LoginPointer create(PasswordLoginPair pair) throws LoginExistsException {

		String username = pair.getUsername();
		String password = pair.getPassword();

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
