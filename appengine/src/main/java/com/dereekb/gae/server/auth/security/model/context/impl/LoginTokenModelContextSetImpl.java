package com.dereekb.gae.server.auth.security.model.context.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.exception.UnavailableModelContextTypeException;
import com.dereekb.gae.server.datastore.models.ModelUtility;

/**
 * {@link LoginTokenModelContextSet} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextSetImpl extends AbstractLoginTokenModelContextSet {

	private Map<String, LoginTokenTypedModelContextSet> setMap;

	public LoginTokenModelContextSetImpl() {
		this(new HashMap<String, LoginTokenTypedModelContextSet>());
	}

	public LoginTokenModelContextSetImpl(List<LoginTokenTypedModelContextSet> list) {
		super();
		this.setSetMap(list);
	}

	public LoginTokenModelContextSetImpl(Map<String, LoginTokenTypedModelContextSet> setMap) {
		super();
		this.setSetMap(setMap);
	}

	public void setSetMap(List<LoginTokenTypedModelContextSet> sets) {
		Map<String, LoginTokenTypedModelContextSet> map = ModelUtility.makeTypedModelMap(sets);
		this.setSetMap(map);
	}

	public void setSetMap(Map<String, LoginTokenTypedModelContextSet> setMap) {
		if (setMap == null) {
			throw new IllegalArgumentException("setMap cannot be null.");
		}

		this.setMap = setMap;
	}

	public void add(LoginTokenTypedModelContextSet contextSet) {
		this.setMap.put(contextSet.getModelType(), contextSet);
	}

	// MARK: LoginTokenModelContextSet
	@Override
	public Map<String, LoginTokenTypedModelContextSet> getSetMap() {
		return this.setMap;
	}

	@Override
	public Set<String> getModelTypes() {
		return this.setMap.keySet();
	}

	@Override
	public boolean hasContextForType(String modelType) {
		return this.setMap.containsKey(modelType);
	}

	@Override
	public LoginTokenTypedModelContextSet tryGetContextsForType(String modelType)
	        throws UnavailableModelContextTypeException {
		return this.setMap.get(modelType);
	}

	@Override
	public String toString() {
		return "LoginTokenModelContextSetImpl [setMap=" + this.setMap + "]";
	}

}
