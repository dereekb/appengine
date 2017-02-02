package com.dereekb.gae.server.auth.model.key.search.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.query.parameters.AbstractOwnedModelQuery;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Utility used for querying a {@link Login}.
 * 
 * @author dereekb
 *
 */
public class LoginKeyQuery extends AbstractOwnedModelQuery
        implements ConfigurableEncodedQueryParameters {

	public static final String LOGIN_POINTER_FIELD = "pointer";

	private static final ModelKeyQueryFieldParameterBuilder LOGIN_POINTER_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NAME_SINGLETON;

	private ModelKeyQueryFieldParameter loginPointer;

	public ModelKeyQueryFieldParameter getLoginPointer() {
		return this.loginPointer;
	}

	public void setLoginPointer(ModelKey loginPointer) {
		this.loginPointer = LOGIN_POINTER_FIELD_BUILDER.make(LOGIN_POINTER_FIELD, loginPointer);
	}

	public void setLoginPointer(String loginPointer) {
		this.loginPointer = LOGIN_POINTER_FIELD_BUILDER.makeModelKeyParameter(LOGIN_POINTER_FIELD, loginPointer);
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = super.getParameters();

		ParameterUtility.put(parameters, this.loginPointer);

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		super.setParameters(parameters);
		this.setLoginPointer(parameters.get(LOGIN_POINTER_FIELD));
	}

}
