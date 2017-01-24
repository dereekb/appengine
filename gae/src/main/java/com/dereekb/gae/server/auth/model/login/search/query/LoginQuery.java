package com.dereekb.gae.server.auth.model.login.search.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.query.parameters.AbstractDateModelQuery;
import com.dereekb.gae.server.auth.model.login.Login;

/**
 * Utility used for querying a {@link Login}.
 * 
 * @author dereekb
 *
 */
public class LoginQuery extends AbstractDateModelQuery {

	// TODO: Add Groups and Roles querying to LoginQuery.

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = super.getParameters();

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {

		super.setParameters(parameters);
	}

}
