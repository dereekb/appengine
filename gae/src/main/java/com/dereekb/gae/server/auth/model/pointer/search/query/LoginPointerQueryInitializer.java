package com.dereekb.gae.server.auth.model.pointer.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.ConfigurableQueryParameters;
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

	public static final String LOGIN_FIELD = "login";

	private static final ObjectifyKeyFieldParameterBuilder<LoginPointer> LOGIN_BUILDER = new ObjectifyKeyFieldParameterBuilder<LoginPointer>(
	        ModelKeyType.NUMBER, LoginPointer.class);

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

		private ObjectifyKeyFieldParameter<LoginPointer> parent;

		public ObjectifyKeyFieldParameter<LoginPointer> getParent() {
			return this.parent;
		}

		public void setParent(Key<LoginPointer> parent) {
			if (parent != null) {
				this.parent = LOGIN_BUILDER.make(LOGIN_FIELD, parent);
			} else {
				this.parent = null;
			}
		}

		// MARK: ConfigurableQueryParameters
		@Override
		public Map<String, String> getParameters() {
			Map<String, String> parameters = new HashMap<String, String>();

			if (this.parent != null) {
				parameters.put(LOGIN_FIELD, this.parent.getParameterString());
			}

			return parameters;
		}

		@Override
		public void setParameters(Map<String, String> parameters) {
			String parentString = parameters.get(LOGIN_FIELD);

			if (parentString != null) {
				this.parent = LOGIN_BUILDER.make(LOGIN_FIELD, parentString);
			} else {
				this.parent = null;
			}

		}

		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {

			if (this.parent != null) {
				this.parent.configure(request);
			}

		}

	}

}
