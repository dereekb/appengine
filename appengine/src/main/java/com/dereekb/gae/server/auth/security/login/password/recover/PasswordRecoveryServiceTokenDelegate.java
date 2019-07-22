package com.dereekb.gae.server.auth.security.login.password.recover;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationException;
import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryVerificationExpiredException;

/**
 * Used to generate tokens for verification and recovery.
 *
 * @author dereekb
 *
 */
public interface PasswordRecoveryServiceTokenDelegate {

	/**
	 * Generates a new password recovery verification token.
	 * <p>
	 * Verification tokens are used to verify a user's email address.
	 *
	 * @param pointer
	 *            {@link LoginPointer}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String generateVerificationToken(LoginPointer pointer);

	/**
	 * Verifies the verification token is valid.
	 *
	 * @param verificationToken
	 *            {@link String}. Never {@code null}.
	 * @throws PasswordRecoveryVerificationExpiredException
	 *             thrown if the token is expired.
	 * @throws PasswordRecoveryVerificationException
	 *             thrown if any other verification exception occurs.
	 */
	public void verifyLoginTokenEmail(String verificationToken)
	        throws PasswordRecoveryVerificationExpiredException,
	            PasswordRecoveryVerificationException;

	/**
	 * Builds a new password recovery token.
	 * <p>
	 * Recovery tokens are used to change a user's password through the recovery
	 * mechanism.
	 *
	 * @param pointer
	 *            {@link LoginPointer}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String generateRecoveryToken(LoginPointer pointer);

	/**
	 * A recovery method to find a {@link LoginPointer} using the user's
	 * username.
	 *
	 * @param username
	 * @return {@link LoginPointer}. Never {@code null}.
	 * @throws UnavailableModelException
	 */
	public LoginPointer loadLoginPointerForUser(String username) throws UnavailableModelException;

	/**
	 * A recovery method to find a {@link LoginPointer} using the user's email.
	 *
	 * @param email
	 * @return {@link LoginPointer}. Never {@code null}.
	 * @throws UnavailableModelException
	 */
	public LoginPointer loadLoginPointerForEmail(String email) throws UnavailableModelException;

}
