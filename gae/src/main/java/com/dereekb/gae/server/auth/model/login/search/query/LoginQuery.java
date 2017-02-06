package com.dereekb.gae.server.auth.model.login.search.query;

import java.util.Map;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.model.query.impl.AbstractOwnedDateModelQuery;

/**
 * Utility used for querying a {@link Login}.
 * 
 * @author dereekb
 *
 */
public class LoginQuery extends AbstractOwnedDateModelQuery {

	// TODO: Add Groups and Roles querying to LoginQuery.

	public LoginQuery() {
		super();
	}

	public LoginQuery(Map<String, String> parameters) throws IllegalArgumentException {
		super(parameters);
	}

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
