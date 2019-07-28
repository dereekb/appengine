package com.dereekb.gae.web.api.auth.controller.model.context.impl;

import java.util.Date;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoder;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextService;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.time.DateUtility;
import com.dereekb.gae.web.api.auth.controller.model.context.LoginTokenModelContextControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Abstract {@link LoginTokenModelContextControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
@Deprecated
public abstract class AbstractLoginTokenModelContextControllerDelegateImpl<T extends LoginToken> extends LimitedLoginTokenModelContextControllerDelegateImpl<T> {

	private static final Long EXPIRATION_TIME = DateUtility.timeInMinutes(5L);
	private static final Long MAX_EXPIRATION_TIME = DateUtility.timeInMinutes(10L);

	private Long expirationTime = EXPIRATION_TIME;
	private Long maxExpirationTime = MAX_EXPIRATION_TIME;

	private LoginTokenEncoderDecoder<T> loginTokenEncoderDecoder;

	public AbstractLoginTokenModelContextControllerDelegateImpl(LoginTokenModelContextService service,
	        LoginTokenModelContextSetEncoder modelContextSetEncoder,
	        LoginTokenEncoderDecoder<T> loginTokenEncoderDecoder) {
		super(service, modelContextSetEncoder);
		this.setService(service);
		this.setLoginTokenEncoderDecoder(loginTokenEncoderDecoder);
		this.setModelContextSetEncoder(modelContextSetEncoder);
	}

	public Long getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		if (expirationTime == null) {
			throw new IllegalArgumentException("expirationTime cannot be null.");
		}

		this.expirationTime = expirationTime;
	}

	public Long getMaxExpirationTime() {
		return this.maxExpirationTime;
	}

	public void setMaxExpirationTime(Long maxExpirationTime) {
		if (maxExpirationTime == null) {
			throw new IllegalArgumentException("maxExpirationTime cannot be null.");
		}

		this.maxExpirationTime = maxExpirationTime;
	}

	public LoginTokenEncoderDecoder<T> getLoginTokenEncoderDecoder() {
		return this.loginTokenEncoderDecoder;
	}

	public void setLoginTokenEncoderDecoder(LoginTokenEncoderDecoder<T> loginTokenEncoderDecoder) {
		if (loginTokenEncoderDecoder == null) {
			throw new IllegalArgumentException("loginTokenEncoderDecoder cannot be null.");
		}

		this.loginTokenEncoderDecoder = loginTokenEncoderDecoder;
	}

	// MARK: LoginTokenModelContextControllerDelegate
	@Override
	public ApiLoginTokenModelContextResponse loginWithContext(ApiLoginTokenModelContextRequest request)
	        throws NoSecurityContextException,
	            AtomicOperationException,
	            UnsupportedOperationException {
		return new FullInstance(request).respond();
	}

	protected class FullInstance extends Instance {

		public FullInstance(ApiLoginTokenModelContextRequest request) {
			super(request);
		}

		@Override
		protected LoginTokenPair makeLoginTokenPair() {
			if (ValueUtility.valueOf(this.request.getMakeContext(), true)) {
				Date expires = getExpirationDate(this.currentToken, this.request.getExpirationTime());

				EncodedLoginTokenModelContextSet encodedSet = AbstractLoginTokenModelContextControllerDelegateImpl.this
				        .getModelContextSetEncoder().encodeSet(this.set);

				T newToken = makeNewToken(this.currentToken, expires, encodedSet);
				String encodedToken = AbstractLoginTokenModelContextControllerDelegateImpl.this.loginTokenEncoderDecoder
				        .encodeLoginToken(newToken);
				return new LoginTokenPair(encodedToken);
			} else {
				return null;
			}
		}

	}

	protected abstract T makeNewToken(T currentToken,
	                                  Date expires,
	                                  EncodedLoginTokenModelContextSet encodedSet);

	// MARK: Internal
	protected Date getExpirationDate(LoginToken token,
	                                 Long expirationTime)
	        throws IllegalArgumentException {

		if (expirationTime == null) {
			expirationTime = this.expirationTime;
		} else if (expirationTime > MAX_EXPIRATION_TIME) {
			throw new IllegalArgumentException(
			        "Expiration time provided was too large. Must be at most: " + this.maxExpirationTime);
		}

		Date currentExpiration = token.getExpiration();
		Date desiredExpiration = DateUtility.getDateIn(expirationTime);

		// Return the smaller of the two dates.
		if (currentExpiration.before(desiredExpiration)) {
			return currentExpiration;
		}

		return desiredExpiration;
	}

	@Override
	public String toString() {
		return "AbstractLoginTokenModelContextControllerDelegateImpl [expirationTime=" + this.expirationTime
		        + ", maxExpirationTime=" + this.maxExpirationTime + ", loginTokenEncoderDecoder="
		        + this.loginTokenEncoderDecoder + ", getService()=" + this.getService()
		        + ", getModelContextSetEncoder()=" + this.getModelContextSetEncoder() + "]";
	}

}
