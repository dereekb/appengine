package com.dereekb.gae.server.auth.security.model.context.service.impl;

import com.dereekb.gae.model.crud.services.request.options.impl.AtomicRequestOptionsImpl;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceRequest;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * {@link LoginTokenModelContextServiceRequest} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextServiceRequestImpl extends AtomicRequestOptionsImpl
        implements LoginTokenModelContextServiceRequest {

	private HashMapWithSet<String, String> typesAndKeys;

	public LoginTokenModelContextServiceRequestImpl(HashMapWithSet<String, String> typesAndKeys) {
		super();
		this.setTypesAndKeys(typesAndKeys);
	}

	public LoginTokenModelContextServiceRequestImpl(HashMapWithSet<String, String> typesAndKeys, boolean atomic) {
		super(atomic);
		this.setTypesAndKeys(typesAndKeys);
	}

	// MARK: LoginTokenModelContextServiceRequest
	@Override
	public HashMapWithSet<String, String> getTypesAndKeys() {
		return this.typesAndKeys;
	}

	public void setTypesAndKeys(HashMapWithSet<String, String> typesAndKeys) {
		if (typesAndKeys == null) {
			throw new IllegalArgumentException("typesAndKeys cannot be null.");
		}

		this.typesAndKeys = typesAndKeys;
	}

	@Override
	public String toString() {
		return "LoginTokenModelContextServiceRequestImpl [typesAndKeys=" + this.typesAndKeys + ", atomic=" + this.atomic
		        + "]";
	}

}
