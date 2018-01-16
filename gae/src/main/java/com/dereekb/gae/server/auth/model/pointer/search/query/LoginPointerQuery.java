package com.dereekb.gae.server.auth.model.pointer.search.query;

import java.util.Map;

import com.dereekb.gae.server.auth.model.login.misc.owned.query.MutableLoginOwnedQuery;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.model.query.impl.AbstractOwnedModelQuery;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.IntegerQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Utility used for querying a {@link LoginPointer}.
 *
 * @author dereekb
 *
 */
public class LoginPointerQuery extends AbstractOwnedModelQuery
        implements ConfigurableEncodedQueryParameters, MutableLoginOwnedQuery {

	public static final String TYPE_FIELD = "type";
	public static final String LOGIN_FIELD = "login";

	private static final ModelKeyQueryFieldParameterBuilder LOGIN_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NUMBER_SINGLETON;

	private IntegerQueryFieldParameter type;
	private ModelKeyQueryFieldParameter login;

	public LoginPointerQuery() {
		super();
	}

	public LoginPointerQuery(Map<String, String> parameters) throws IllegalArgumentException {
		super(parameters);
	}

	public IntegerQueryFieldParameter getType() {
		return this.type;
	}

	public void setType(LoginPointerType type) {
		Integer typeInteger = null;

		if (type != null) {
			typeInteger = type.code;
		}

		this.setType(typeInteger);
	}

	public void setType(String type) {
		this.type = IntegerQueryFieldParameter.make(TYPE_FIELD, type);
	}

	public void setType(Integer type) {
		this.type = IntegerQueryFieldParameter.make(TYPE_FIELD, type);
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
		ParameterUtility.put(parameters, this.type);
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		super.setParameters(parameters);
		this.setLogin(parameters.get(LOGIN_FIELD));
		this.setType(parameters.get(TYPE_FIELD));
	}

}
