package com.dereekb.gae.web.api.auth.controller.model.impl;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextService;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceRequest;
import com.dereekb.gae.server.auth.security.model.context.service.impl.LoginTokenModelContextServiceRequestImpl;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapAndSet;
import com.dereekb.gae.web.api.auth.controller.model.LoginTokenModelContextControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link LoginTokenModelContextControllerDelegate} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextControllerDelegateImpl
        implements LoginTokenModelContextControllerDelegate {

	private LoginTokenModelContextService service;

	public LoginTokenModelContextService getService() {
		return this.service;
	}

	public void setService(LoginTokenModelContextService service) {
		if (service == null) {
			throw new IllegalArgumentException("service cannot be null.");
		}

		this.service = service;
	}

	// MARK: LoginTokenModelContextControllerDelegate
	@Override
	public LoginTokenPair loginWithContext(ApiLoginTokenModelContextRequest request) throws AtomicOperationException {

		LoginTokenModelContextServiceRequest serviceRequest = this.makeRequest(request);

		this.service.makeContextSet(serviceRequest);
		
		return null;
	}

	private LoginTokenModelContextServiceRequest makeRequest(ApiLoginTokenModelContextRequest request) {

		CaseInsensitiveMapAndSet map = new CaseInsensitiveMapAndSet();
		List<ApiLoginTokenModelContextTypeImpl> types = request.getData();

		for (ApiLoginTokenModelContextTypeImpl type : types) {
			String modelType = type.getModelType();
			Set<String> keys = type.getKeys();
			
			map.put(modelType, keys);
		}

		return new LoginTokenModelContextServiceRequestImpl(map, request.isAtomic());
	}

}
