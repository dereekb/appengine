package com.dereekb.gae.web.api.auth.controller.model.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoder;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextService;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceRequest;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceResponse;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceRequestImpl;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapAndSet;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.time.DateUtility;
import com.dereekb.gae.web.api.auth.controller.model.ApiLoginTokenModelContextResponse;
import com.dereekb.gae.web.api.auth.controller.model.LoginTokenModelContextControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Abstract {@link LoginTokenModelContextControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractLoginTokenModelContextControllerDelegateImpl<T extends LoginToken>
        implements LoginTokenModelContextControllerDelegate {

	private static final Long EXPIRATION_TIME = DateUtility.timeInMinutes(5L);
	private static final Long MAX_EXPIRATION_TIME = DateUtility.timeInMinutes(10L);

	private Long expirationTime = EXPIRATION_TIME;
	private Long maxExpirationTime = MAX_EXPIRATION_TIME;

	private LoginTokenModelContextService service;
	private LoginTokenEncoderDecoder<T> loginTokenEncoderDecoder;
	private LoginTokenModelContextSetEncoder modelContextSetEncoder;

	public AbstractLoginTokenModelContextControllerDelegateImpl(LoginTokenModelContextService service,
	        LoginTokenEncoderDecoder<T> loginTokenEncoderDecoder,
	        LoginTokenModelContextSetEncoder modelContextSetEncoder) {
		super();
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

	public LoginTokenModelContextService getService() {
		return this.service;
	}

	public void setService(LoginTokenModelContextService service) {
		if (service == null) {
			throw new IllegalArgumentException("service cannot be null.");
		}

		this.service = service;
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

	public LoginTokenModelContextSetEncoder getModelContextSetEncoder() {
		return this.modelContextSetEncoder;
	}

	public void setModelContextSetEncoder(LoginTokenModelContextSetEncoder modelContextSetEncoder) {
		if (modelContextSetEncoder == null) {
			throw new IllegalArgumentException("modelContextSetEncoder cannot be null.");
		}

		this.modelContextSetEncoder = modelContextSetEncoder;
	}

	// MARK: LoginTokenModelContextControllerDelegate
	@Override
	public ApiLoginTokenModelContextResponse loginWithContext(ApiLoginTokenModelContextRequest request)
	        throws NoSecurityContextException,
	            AtomicOperationException {

		T currentToken = this.getCurrentToken();
		Date expires = this.getExpirationDate(currentToken, request.getExpirationTime());

		LoginTokenModelContextServiceRequest serviceRequest = this.makeRequest(request);
		LoginTokenModelContextServiceResponse serviceResponse = this.service.makeContextSet(serviceRequest);

		LoginTokenModelContextSet set = serviceResponse.getContextSet();

		LoginTokenPair pair = null;
		ApiModelRolesResponseData rolesData = null;

		if (ValueUtility.valueOf(request.getMakeContext(), true)) {
			EncodedLoginTokenModelContextSet encodedSet = this.modelContextSetEncoder.encodeSet(set);

			T newToken = this.makeNewToken(currentToken, expires, encodedSet);
			String encodedToken = this.loginTokenEncoderDecoder.encodeLoginToken(newToken);
			pair = new LoginTokenPair(encodedToken);
		}

		if (ValueUtility.valueOf(request.getIncludeRoles())) {
			rolesData = this.makeRolesResponseData(set);
		}

		return new ApiLoginTokenModelContextResponseImpl(pair, rolesData);
	}

	@Override
	public ApiModelRolesResponseData readRoles(ApiModelRolesRequest request)
	        throws NoSecurityContextException,
	            AtomicOperationException {

		LoginTokenModelContextServiceRequest serviceRequest = this.makeRequest(request);
		LoginTokenModelContextServiceResponse serviceResponse = this.service.makeContextSet(serviceRequest);

		LoginTokenModelContextSet contextSet = serviceResponse.getContextSet();
		return this.makeRolesResponseData(contextSet);
	}

	private ApiModelRolesResponseData makeRolesResponseData(LoginTokenModelContextSet contextSet) {
		return ApiModelRolesResponseData.makeWithContextSet(contextSet);
	}

	protected abstract T getCurrentToken() throws NoSecurityContextException;

	protected abstract T makeNewToken(T currentToken,
	                                  Date expires,
	                                  EncodedLoginTokenModelContextSet encodedSet);

	private Date getExpirationDate(LoginToken token,
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

	private LoginTokenModelContextServiceRequest makeRequest(ApiModelRolesRequest request) {

		CaseInsensitiveMapAndSet map = new CaseInsensitiveMapAndSet();
		List<ApiLoginTokenModelContextTypeImpl> types = request.getData();

		for (ApiLoginTokenModelContextTypeImpl type : types) {
			String modelType = type.getModelType();
			Set<String> keys = type.getKeys();
			map.put(modelType, keys);
		}

		return new LoginTokenModelContextServiceRequestImpl(map, request.isAtomic());
	}

	@Override
	public String toString() {
		return "AbstractLoginTokenModelContextControllerDelegateImpl [expirationTime=" + this.expirationTime
		        + ", maxExpirationTime=" + this.maxExpirationTime + ", service=" + this.service
		        + ", loginTokenEncoderDecoder=" + this.loginTokenEncoderDecoder + ", modelContextSetEncoder="
		        + this.modelContextSetEncoder + "]";
	}

}
