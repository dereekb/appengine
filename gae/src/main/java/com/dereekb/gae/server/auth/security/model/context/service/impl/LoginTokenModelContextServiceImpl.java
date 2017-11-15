package com.dereekb.gae.server.auth.security.model.context.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.impl.LoginTokenModelContextSetImpl;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextService;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceEntry;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceRequest;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceResponse;
import com.dereekb.gae.server.datastore.models.impl.AbstractTypedModelMap;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * {@link LoginTokenModelContextService} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextServiceImpl extends AbstractTypedModelMap<LoginTokenModelContextServiceEntry>
        implements LoginTokenModelContextService {

	public LoginTokenModelContextServiceImpl(List<LoginTokenModelContextServiceEntry> typeMap) {
		super(typeMap);
	}

	// MARK: LoginTokenModelContextService
	@Override
	public LoginTokenModelContextServiceResponse makeContextSet(LoginTokenModelContextServiceRequest request) {
		return new Instance(request).makeContextSet();
	}

	private class Instance {

		private final LoginTokenModelContextServiceRequest request;

		public Instance(LoginTokenModelContextServiceRequest request) {
			this.request = request;
		}

		// MARK: Context Set
		public LoginTokenModelContextServiceResponse makeContextSet() throws AtomicOperationException {
			HashMapWithSet<String, String> map = this.request.getTypesAndKeys();
			List<LoginTokenTypedModelContextSet> list = new ArrayList<LoginTokenTypedModelContextSet>();

			for (Entry<String, Set<String>> entry : map.entrySet()) {
				list.add(this.makeTypedModelContextSet(entry));
			}

			LoginTokenModelContextSetImpl set = new LoginTokenModelContextSetImpl(list);
			return new LoginTokenModelContextServiceResponseImpl(set);
		}

		private LoginTokenTypedModelContextSet makeTypedModelContextSet(Entry<String, Set<String>> entry)
		        throws AtomicOperationException {
			String type = entry.getKey();
			Set<String> keys = entry.getValue();
			LoginTokenModelContextServiceEntry serviceEntry = LoginTokenModelContextServiceImpl.this
			        .getEntryForType(type);
			return serviceEntry.makeTypedContextSet(keys, this.request.isAtomic());
		}

	}

	@Override
	public String toString() {
		return "LoginTokenModelContextServiceImpl [getTypeMap()=" + this.getTypeMap() + "]";
	}

}
