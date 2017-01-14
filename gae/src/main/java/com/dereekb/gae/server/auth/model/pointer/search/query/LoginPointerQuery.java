package com.dereekb.gae.server.auth.model.pointer.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
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
public class LoginPointerQuery
        implements ConfigurableEncodedQueryParameters {

	public static final String TYPE_FIELD = "type";
	public static final String LOGIN_FIELD = "login";

	private static final ModelKeyQueryFieldParameterBuilder LOGIN_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NUMBER_SINGLETON;

	private IntegerQueryFieldParameter type;
	private ModelKeyQueryFieldParameter login;

	public IntegerQueryFieldParameter getType() {
		return this.type;
	}

	public void setType(LoginPointerType type) {
		Integer typeInteger = null;

		if (type != null) {
			typeInteger = type.id;
		}

		this.setType(typeInteger);
	}

	public void setType(Integer type) {
		if (type != null) {
			this.type = new IntegerQueryFieldParameter(TYPE_FIELD, type);
		} else {
			this.type = null;
		}
	}

	public ModelKeyQueryFieldParameter getLogin() {
		return this.login;
	}

	public void setLogin(ModelKey login) {
		if (login != null) {
			this.login = LOGIN_FIELD_BUILDER.make(LOGIN_FIELD, login);
		} else {
			this.login = null;
		}
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();

		if (this.login != null) {
			parameters.put(LOGIN_FIELD, this.login.getParameterString());
		}

		if (this.type != null) {
			parameters.put(TYPE_FIELD, this.type.getParameterString());
		}

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		String loginString = parameters.get(LOGIN_FIELD);
		String typeString = parameters.get(TYPE_FIELD);

		if (loginString != null) {
			this.login = LOGIN_FIELD_BUILDER.makeModelKeyParameter(LOGIN_FIELD, loginString);
		} else {
			this.login = null;
		}

		if (typeString != null) {
			try {
				Integer typeInteger = new Integer(typeString);
				this.setType(typeInteger);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			this.type = null;
		}

	}

}
