package com.dereekb.gae.server.auth.model.key.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Utility used for querying a {@link Login}.
 * 
 * @author dereekb
 *
 */
public class LoginKeyQuery
        implements ConfigurableEncodedQueryParameters {

	public static final String LOGIN_POINTER_FIELD = "pointer";

	private static final ModelKeyQueryFieldParameterBuilder LOGIN_POINTER_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NAME_SINGLETON;

	private ModelKeyQueryFieldParameter loginPointer;

	public ModelKeyQueryFieldParameter getLoginPointer() {
		return this.loginPointer;
	}

	public void setLoginPointer(ModelKey loginPointer) {
		if (loginPointer != null) {
			this.loginPointer = LOGIN_POINTER_FIELD_BUILDER.make(LOGIN_POINTER_FIELD, loginPointer);
		} else {
			this.loginPointer = null;
		}
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();

		if (this.loginPointer != null) {
			parameters.put(LOGIN_POINTER_FIELD, this.loginPointer.getParameterString());
		}

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		String loginString = parameters.get(LOGIN_POINTER_FIELD);

		if (loginString != null) {
			this.loginPointer = LOGIN_POINTER_FIELD_BUILDER.makeModelKeyParameter(LOGIN_POINTER_FIELD, loginString);
		} else {
			this.loginPointer = null;
		}

	}

}
