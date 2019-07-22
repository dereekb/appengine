package com.dereekb.gae.web.api.auth.controller.password.impl;

import com.dereekb.gae.server.auth.security.login.exception.InvalidLoginCredentialsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginPair;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.NoPasswordRecoveryAddressException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryMailException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerifiedException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnknownUsernameException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnregisteredEmailException;
import com.dereekb.gae.web.api.auth.controller.password.PasswordLoginControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link PasswordLoginControllerDelegate} implemention that throws {@link UnsupportedOperationException} when
 *
 * @author dereekb
 *
 */
public class RestrictedPasswordLoginControllerDelegateImpl
        implements PasswordLoginControllerDelegate {

	// MARK: PasswordLoginControllerDelegate
	@Override
	public void sendVerificationEmail(String username)
	        throws NoPasswordRecoveryAddressException,
	            PasswordRecoveryVerifiedException,
	            UnknownUsernameException,
	            PasswordRecoveryMailException {
		this.throwRestrictedException();
	}

	@Override
	public void verifyUserEmail(String verificationToken)
	        throws PasswordRecoveryVerifiedException,
	            PasswordRecoveryVerificationException {
		this.throwRestrictedException();
	}

	@Override
	public void recoverPassword(String username) throws NoPasswordRecoveryAddressException, UnknownUsernameException {
		this.throwRestrictedException();
	}

	@Override
	public void recoverUsername(String email) throws NoPasswordRecoveryAddressException, UnregisteredEmailException {
		this.throwRestrictedException();
	}

	@Override
	public LoginTokenPair createLogin(PasswordLoginPair pair) throws LoginExistsException {
		this.throwRestrictedException();
		return null;
	}

	@Override
	public LoginTokenPair login(PasswordLoginPair pair)
	        throws LoginUnavailableException,
	            InvalidLoginCredentialsException {
		this.throwRestrictedException();
		return null;
	}

	// MARK: Internal
	public void throwRestrictedException() {
		throw new UnsupportedOperationException("Password logins are restricted.");
	}

}
