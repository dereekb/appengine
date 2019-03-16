package com.dereekb.gae.server.auth.security.login.password.recover;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Used to generate tokens for verification and recovery.
 *
 * @author dereekb
 *
 */
public interface PasswordRecoveryServiceTokenDelegate {

	public String generateVerificationToken(LoginPointer pointer);

	public void verifyLoginTokenEmail(String verificationToken);

	public String generateRecoveryToken(LoginPointer pointer);

	public LoginPointer loadLoginPointerForUser(String username) throws UnavailableModelException;

	public LoginPointer loadLoginPointerForEmail(String email) throws UnavailableModelException;

}
