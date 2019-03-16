package com.dereekb.gae.server.auth.security.login.password.exception;

import com.dereekb.gae.server.auth.security.login.password.recover.exception.PasswordRecoveryException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * {@link PasswordRecoveryException} that is related to
 * {@link PasswordRestriction].
 *
 * @author dereekb
 *
 */
public class PasswordRestrictionException extends PasswordException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "PASSWORD_RESTRICTION";
	public static final String ERROR_TITLE = "Password Restriction";

	private String reason;
	private Object data;

	public PasswordRestrictionException(String reason) {
		this(reason, null);
	}

	public PasswordRestrictionException(String reason, Object data) {
		this.setReason(reason);
		this.setData(data);
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, this.reason, this.data);
	}

	@Override
	public String toString() {
		return "PasswordRestrictionException [reason=" + this.reason + "]";
	}

}
