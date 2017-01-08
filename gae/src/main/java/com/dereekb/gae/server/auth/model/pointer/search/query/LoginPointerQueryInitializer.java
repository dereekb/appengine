package com.dereekb.gae.server.auth.model.pointer.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.ConfigurableQueryParameters;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.IntegerQueryFieldParameter;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyKeyFieldParameterBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyKeyFieldParameterBuilder.ObjectifyKeyFieldParameter;
import com.googlecode.objectify.Key;

/**
 * {@link ConfigurableQueryParameters} implementation for a
 * {@link LoginPointerQuery}.
 *
 * @author dereekb
 *
 */
public class LoginPointerQueryInitializer
        implements ObjectifyQueryRequestLimitedBuilderInitializer {

	public static final String TYPE_FIELD = "type";
	public static final String LOGIN_FIELD = "login";

	private static final ObjectifyKeyFieldParameterBuilder<Login> LOGIN_BUILDER = new ObjectifyKeyFieldParameterBuilder<Login>(
	        ModelKeyType.NUMBER, Login.class);

	@Override
	public void initalizeBuilder(ObjectifyQueryRequestLimitedBuilder builder,
	                             Map<String, String> parameters) {

		LoginPointerQuery query = new LoginPointerQuery();

		if (parameters != null) {
			query.setParameters(parameters);
			query.configure(builder);
		}

	}

	public static class LoginPointerQuery
	        implements ConfigurableQueryParameters {

		private IntegerQueryFieldParameter type;
		private ObjectifyKeyFieldParameter<Login> login;

		public IntegerQueryFieldParameter getType() {
			return type;
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

		public ObjectifyKeyFieldParameter<Login> getLogin() {
			return this.login;
		}

		public void setLogin(Key<Login> login) {
			if (login != null) {
				this.login = LOGIN_BUILDER.make(LOGIN_FIELD, login);
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
				this.login = LOGIN_BUILDER.make(LOGIN_FIELD, loginString);
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

		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {

			if (this.login != null) {
				this.login.configure(request);
			}

			if (this.type != null) {
				this.type.configure(request);
			}

		}

	}

}
