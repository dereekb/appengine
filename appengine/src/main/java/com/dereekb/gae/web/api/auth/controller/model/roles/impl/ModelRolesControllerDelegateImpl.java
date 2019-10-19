package com.dereekb.gae.web.api.auth.controller.model.roles.impl;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoder;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextService;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceRequest;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceResponse;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceRequestImpl;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapAndSet;
import com.dereekb.gae.web.api.auth.controller.model.roles.ModelRolesControllerDelegate;

/**
 * {@link ModelRolesControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class ModelRolesControllerDelegateImpl
        implements ModelRolesControllerDelegate {

	private LoginTokenModelContextService service;
	private LoginTokenModelContextSetEncoder modelContextSetEncoder;

	public ModelRolesControllerDelegateImpl(LoginTokenModelContextService service,
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

	// MARK: ModelRolesControllerDelegate
	@Override
	public ApiModelRolesResponseData readRoles(ApiModelRolesRequest request)
	        throws NoSecurityContextException,
	            AtomicOperationException {

		LoginTokenModelContextServiceRequest serviceRequest = this.makeRequest(request);
		LoginTokenModelContextServiceResponse serviceResponse = this.service.makeContextSet(serviceRequest);

		LoginTokenModelContextSet contextSet = serviceResponse.getContextSet();
		return this.makeRolesResponseData(contextSet);
	}

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

	protected ApiModelRolesResponseData makeRolesResponseData(LoginTokenModelContextSet contextSet) {
		return ApiModelRolesResponseData.makeWithContextSet(contextSet);
	}

}
