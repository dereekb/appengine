package com.dereekb.gae.server.auth.security.login.password.recover;

import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryMailException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnknownUsernameException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.UnregisteredEmailException;

/**
 * Password recovery service that is used to send/receive e-mails related to
 * password recovery and verification.
 *
 * @author dereekb
 *
 */
public interface PasswordRecoveryService {

	/**
	 * Sends a verification email to the user's email address.
	 *
	 * @param email
	 *            {@link String}. Never {@code null}.
	 * @throws PasswordRecoveryMailException
	 *             thrown if an error occurs while trying to send the recovery
	 *             mail.
	 */
	public void sendVerificationEmail(String email) throws PasswordRecoveryMailException;

	/**
	 * Verifies a user's email address using the verification token.
	 * Verification tokens are sent to the user's email address.
	 *
	 * @param verificationToken
	 *            {@link String}. Never {@code null}.
	 * @throws PasswordRecoveryVerificationException
	 *             thrown if an error occurs while trying to verify the user
	 *             email.
	 */
	public void verifyUserEmail(String verificationToken) throws PasswordRecoveryVerificationException;

	/**
	 * Sends a recover password email, if the user exists.
	 *
	 * @param username
	 *            {@link String}. Never {@code null}.
	 * @throws UnknownUsernameException
	 *             thrown if the user does not exist.
	 */
	public void recoverPassword(String username) throws UnknownUsernameException;

	/**
	 * Sends an email to the input email, if a user is registered with that
	 * email.
	 *
	 * @param email
	 *            {@link String}. Never {@code null}.
	 * @throws UnregisteredEmailException
	 *             thrown if the email does not exist.
	 */
	public void recoverUsername(String email) throws UnregisteredEmailException;

}
