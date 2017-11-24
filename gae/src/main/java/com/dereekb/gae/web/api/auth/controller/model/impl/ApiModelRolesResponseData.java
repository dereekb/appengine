package com.dereekb.gae.web.api.auth.controller.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * {@link ApiResponseData} for a {@link ApiModeleRolesRequest} response.
 *
 * @author dereekb
 *
 */
public class ApiModelRolesResponseData
        implements ApiResponseData {

	public static final String DATA_TYPE = "MODEL_ROLES_RESPONSE_DATA";

	private Map<String, Map<String, Set<String>>> results;

	public ApiModelRolesResponseData() {}

	public ApiModelRolesResponseData(Map<String, Map<String, Set<String>>> results) {
		this.setResults(results);
	}

	public Map<String, Map<String, Set<String>>> getResults() {
		return this.results;
	}

	public void setResults(Map<String, Map<String, Set<String>>> results) {
		this.results = results;
	}

	/**
	 * Creates a new response data from the input context set.s
	 *
	 * @param contextSet
	 *            {@link LoginTokenModelContextSet}. Never {@code null}.
	 * @return {@link ApiModelRolesResponseData}. Never {@code null}.
	 */
	public static ApiModelRolesResponseData makeWithContextSet(LoginTokenModelContextSet contextSet) {
		Map<String, LoginTokenTypedModelContextSet> map = contextSet.getSetMap();
		Map<String, Map<String, Set<String>>> results = new HashMap<String, Map<String, Set<String>>>();

		for (Entry<String, LoginTokenTypedModelContextSet> entry : map.entrySet()) {
			String modelType = entry.getKey();
			LoginTokenTypedModelContextSet set = entry.getValue();
			List<LoginTokenModelContext> contexts = set.getContexts();

			Map<String, Set<String>> modelsMap = new HashMap<String, Set<String>>();

			for (LoginTokenModelContext context : contexts) {
				String modelKeyString = context.getModelKey().toString();

				ModelRoleSet roleSet = context.getRoleSet();
				Set<String> roles = ModelRoleSetUtility.readRoles(roleSet);

				modelsMap.put(modelKeyString, roles);
			}

			results.put(modelType, modelsMap);
		}

		return new ApiModelRolesResponseData(results);
	}

	// MARK: ApiResponseData
	@JsonIgnore
	@Override
	public String getResponseDataType() {
		return DATA_TYPE;
	}

	@JsonIgnore
	@Override
	public Object getResponseData() {
		return this;
	}

	@Override
	public String toString() {
		return "ApiModelRolesResponseData [results=" + this.results + "]";
	}

}
