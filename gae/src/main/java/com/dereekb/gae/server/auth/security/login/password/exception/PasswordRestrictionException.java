package com.dereekb.gae.server.auth.security.login.password.exception;

import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * {@link PasswordRecoveryException} that is related to {@link PasswordRestriction].
 *
 * @author dereekb
 *
 */
public class PasswordRestrictionException extends PasswordException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "PASSWORD_RESTRICTION";
	public static final String ERROR_TITLE = "Password Restriction";

	private String reason;

	public PasswordRestrictionException(String reason) {
		this.setReason(reason);
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, this.reason);
	}

	@Override
	public String toString() {
		return "PasswordRestrictionException [reason=" + this.reason + "]";
	}

}
