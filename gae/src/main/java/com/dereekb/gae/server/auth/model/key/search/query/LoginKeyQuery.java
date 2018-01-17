package com.dereekb.gae.server.auth.model.key.search.query;

import java.util.Map;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.owned.query.MutableLoginOwnedQuery;
import com.dereekb.gae.server.auth.model.pointer.misc.owned.query.MutableLoginPointerOwnedQuery;
import com.dereekb.gae.server.auth.security.model.query.impl.AbstractOwnedModelQuery;
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
        implements ConfigurableEncodedQueryParameters, MutableLoginOwnedQuery, MutableLoginPointerOwnedQuery {

	public static final String LOGIN_FIELD = "login";
	public static final String LOGIN_POINTER_FIELD = "pointer";

	private static final ModelKeyQueryFieldParameterBuilder LOGIN_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NUMBER_SINGLETON;
	private static final ModelKeyQueryFieldParameterBuilder LOGIN_POINTER_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NAME_SINGLETON;

	private ModelKeyQueryFieldParameter login;
	private ModelKeyQueryFieldParameter loginPointer;

	public LoginKeyQuery() {
		super();
	}

	public LoginKeyQuery(Map<String, String> parameters) throws IllegalArgumentException {
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

	@Override
	public ModelKeyQueryFieldParameter getLoginPointer() {
		return this.loginPointer;
	}

	@Override
	public void setLoginPointer(ModelKey loginPointer) {
		this.loginPointer = LOGIN_POINTER_FIELD_BUILDER.make(LOGIN_POINTER_FIELD, loginPointer);
	}

	@Override
	public void setLoginPointer(String loginPointer) {
		this.loginPointer = LOGIN_POINTER_FIELD_BUILDER.makeModelKeyParameter(LOGIN_POINTER_FIELD, loginPointer);
	}

	@Override
	public void setLoginPointer(ModelKeyQueryFieldParameter loginPointer) {
		this.loginPointer = LOGIN_POINTER_FIELD_BUILDER.make(LOGIN_POINTER_FIELD, loginPointer);
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = super.getParameters();

		ParameterUtility.put(parameters, this.login);
		ParameterUtility.put(parameters, this.loginPointer);

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		super.setParameters(parameters);
		this.setLogin(parameters.get(LOGIN_FIELD));
		this.setLoginPointer(parameters.get(LOGIN_POINTER_FIELD));
	}

}
