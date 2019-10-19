package com.dereekb.gae.web.api.auth.controller.model.context.impl;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoder;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextService;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceRequest;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceResponse;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceRequestImpl;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapAndSet;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.web.api.auth.controller.model.context.LoginTokenModelContextControllerDelegate;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesRequest;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesResponseData;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesTypedKeysRequestImpl;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Limited {@link LoginTokenModelContextControllerDelegate} implementation that
 * does not support building new contexts.
 *
 * @author dereekb
 */
@Deprecated
public class LimitedLoginTokenModelContextControllerDelegateImpl<T extends LoginToken>
        implements LoginTokenModelContextControllerDelegate {

	private LoginTokenModelContextService service;
	private LoginTokenModelContextSetEncoder modelContextSetEncoder;

	public LimitedLoginTokenModelContextControllerDelegateImpl(LoginTokenModelContextService service,
	        LoginTokenModelContextSetEncoder modelContextSetEncoder) {
		super();
		this.setService(service);
		this.setModelContextSetEncoder(modelContextSetEncoder);
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
	            AtomicOperationException,
	            UnsupportedOperationException {

		if (ValueUtility.valueOf(request.getMakeContext(), false)) {
			throw new UnsupportedOperationException();
		}

		return new Instance(request).respond();
	}

	protected class Instance {

		protected final ApiLoginTokenModelContextRequest request;

		protected transient T currentToken;

		protected transient LoginTokenModelContextServiceRequest serviceRequest;
		protected transient LoginTokenModelContextServiceResponse serviceResponse;

		protected transient LoginTokenModelContextSet set;

		protected transient LoginTokenPair pair = null;
		protected transient ApiModelRolesResponseData rolesData = null;

		public Instance(ApiLoginTokenModelContextRequest request) {
			this.request = request;
		}

		public ApiLoginTokenModelContextResponse respond() {
			this.init();
			LoginTokenPair pair = this.makeLoginTokenPair();
			ApiModelRolesResponseData rolesData = this.makeResponseData();
			return new ApiLoginTokenModelContextResponseImpl(pair, rolesData);
		}

		protected void init() {
			this.currentToken = getCurrentToken();

			this.serviceRequest = makeRequest(this.request);
			this.serviceResponse = LimitedLoginTokenModelContextControllerDelegateImpl.this.service
			        .makeContextSet(this.serviceRequest);

			this.set = this.serviceResponse.getContextSet();
		}

		protected LoginTokenPair makeLoginTokenPair() {
			return null;
		}

		protected ApiModelRolesResponseData makeResponseData() {
			ApiModelRolesResponseData rolesData = null;

			if (ValueUtility.valueOf(this.request.getIncludeRoles())) {
				rolesData = makeRolesResponseData(this.set);
			}

			return rolesData;
		}

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

	protected ApiModelRolesResponseData makeRolesResponseData(LoginTokenModelContextSet contextSet) {
		return ApiModelRolesResponseData.makeWithContextSet(contextSet);
	}

	@SuppressWarnings("unchecked")
	protected T getCurrentToken() throws NoSecurityContextException {
		return (T) LoginSecurityContext.getAuthentication().getCredentials().getLoginToken();
	}

	// MARK: Internal
	protected LoginTokenModelContextServiceRequest makeRequest(ApiModelRolesRequest request) {

		CaseInsensitiveMapAndSet map = new CaseInsensitiveMapAndSet();
		List<ApiModelRolesTypedKeysRequestImpl> types = request.getData();

		for (ApiModelRolesTypedKeysRequestImpl type : types) {
			String modelType = type.getModelType();
			Set<String> keys = type.getKeys();
			map.put(modelType, keys);
		}

		return new LoginTokenModelContextServiceRequestImpl(map, request.isAtomic());
	}

}
