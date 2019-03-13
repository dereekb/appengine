package com.dereekb.gae.server.auth.security.model.context.encoded.impl;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.exception.UnavailableModelContextTypeException;

/**
 * Empty {@link LoginTokenModelContextSet}.
 *
 * @author dereekb
 *
 */
public class EmptyLoginTokenModelContextSet
        implements LoginTokenModelContextSet {

	private static final Map<String, LoginTokenTypedModelContextSet> MAP = Collections.emptyMap();

	@Override
	public Map<String, LoginTokenTypedModelContextSet> getSetMap() {
		return MAP;
	}

	@Override
	public Set<String> getModelTypes() {
		return Collections.emptySet();
	}

	@Override
	public boolean hasContextForType(String modelType) {
		return false;
	}

	@Override
	public LoginTokenTypedModelContextSet getContextsForType(String modelType)
	        throws UnavailableModelContextTypeException {
		throw new UnavailableModelContextTypeException();
	}

	@Override
	public String toString() {
		return "EmptyLoginTokenModelContextSet []";
	}

}
