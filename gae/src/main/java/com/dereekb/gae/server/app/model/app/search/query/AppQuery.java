package com.dereekb.gae.server.app.model.app.search.query;

import java.util.Map;

import com.dereekb.gae.server.auth.model.login.misc.owned.query.MutableLoginOwnedQuery;
import com.dereekb.gae.server.auth.security.model.query.impl.AbstractOwnedModelQuery;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Utility used for querying an {@link App}.
 *
 * @author dereekb
 *
 */
public class AppQuery extends AbstractOwnedModelQuery
        implements ConfigurableEncodedQueryParameters, MutableLoginOwnedQuery {

	public static final String LOGIN_FIELD = "login";

	private static final ModelKeyQueryFieldParameterBuilder LOGIN_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NUMBER_SINGLETON;

	private ModelKeyQueryFieldParameter login;

	public AppQuery() {
		super();
	}

	public AppQuery(Map<String, String> parameters) throws IllegalArgumentException {
		super(parameters);
	}

	@Override
	public ModelKeyQueryFieldParameter getLogin() {
		return this.login;
	}

	@Override
	public void setLogin(ModelKey login) {
		this.login = LOGIN_FIELD_BUILDER.make(LOGIN_FIELD, login);
	}

	@Override
	public void setLogin(String login) {
		this.login = LOGIN_FIELD_BUILDER.makeModelKeyParameter(LOGIN_FIELD, login);
	}

	@Override
	public void setLogin(ModelKeyQueryFieldParameter login) {
		this.login = LOGIN_FIELD_BUILDER.make(LOGIN_FIELD, login);
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = super.getParameters();

		ParameterUtility.put(parameters, this.login);

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		super.setParameters(parameters);
		this.setLogin(parameters.get(LOGIN_FIELD));
	}

}
