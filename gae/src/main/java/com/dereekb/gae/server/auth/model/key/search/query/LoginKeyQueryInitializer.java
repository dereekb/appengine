package com.dereekb.gae.server.auth.model.key.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyKeyFieldParameterBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyKeyFieldParameterBuilder.ObjectifyKeyFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableQueryParameters;
import com.googlecode.objectify.Key;

/**
 * {@link ConfigurableQueryParameters} implementation for a
 * {@link LoginKey}.
 *
 * @author dereekb
 *
 */
public class LoginKeyQueryInitializer
        implements ObjectifyQueryRequestLimitedBuilderInitializer {

	public static final String LOGIN_POINTER_FIELD = "pointer";

	private static final ObjectifyKeyFieldParameterBuilder<LoginPointer> LOGIN_POINTER_BUILDER = new ObjectifyKeyFieldParameterBuilder<LoginPointer>(
	        ModelKeyType.NAME, LoginPointer.class);

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

		private ObjectifyKeyFieldParameter<LoginPointer> loginPointer;

		public ObjectifyKeyFieldParameter<LoginPointer> getLoginPointer() {
			return this.loginPointer;
		}

		public void setLoginPointer(Key<LoginPointer> login) {
			if (login != null) {
				this.loginPointer = LOGIN_POINTER_BUILDER.make(LOGIN_POINTER_FIELD, login);
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
				this.loginPointer = LOGIN_POINTER_BUILDER.make(LOGIN_POINTER_FIELD, loginString);
			} else {
				this.loginPointer = null;
			}

		}

		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {

			if (this.loginPointer != null) {
				this.loginPointer.configure(request);
			}

		}

	}

}
