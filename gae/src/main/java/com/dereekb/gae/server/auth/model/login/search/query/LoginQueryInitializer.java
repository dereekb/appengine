package com.dereekb.gae.server.auth.model.login.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyDateQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableQueryParameters;

/**
 * {@link ConfigurableQueryParameters} implementation for a {@link LoginQuery}.
 *
 * @author dereekb
 *
 */
public class LoginQueryInitializer
        implements ObjectifyQueryRequestLimitedBuilderInitializer {

	public static final String DATE_FIELD = "date";

	@Override
	public void initalizeBuilder(ObjectifyQueryRequestLimitedBuilder builder,
	                             Map<String, String> parameters) {
		LoginQuery query = new LoginQuery();

		if (parameters != null) {
			query.setParameters(parameters);
			query.configure(builder);
		}
	}

	public static class LoginQuery
	        implements ConfigurableQueryParameters {

		private ObjectifyDateQueryFieldParameter date;

		public ObjectifyDateQueryFieldParameter getDate() {
			return this.date;
		}

		public void setDate(ObjectifyDateQueryFieldParameter date) {
			this.date = date;
		}

		// MARK: ConfigurableQueryParameters
		@Override
		public Map<String, String> getParameters() {
			Map<String, String> parameters = new HashMap<String, String>();

			if (this.date != null) {
				parameters.put(DATE_FIELD, this.date.getParameterString());
			}

			return parameters;
		}

		@Override
		public void setParameters(Map<String, String> parameters) {
			String dateString = parameters.get(DATE_FIELD);

			if (dateString != null) {
				this.date = new ObjectifyDateQueryFieldParameter(DATE_FIELD, dateString);
			} else {
				this.date = null;
			}
		}

		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {

			if (this.date != null) {
				this.date.configure(request);
			}

		}

		@Override
		public String toString() {
			return "LoginQuery [date=" + this.date + "]";
		}

	}

}
