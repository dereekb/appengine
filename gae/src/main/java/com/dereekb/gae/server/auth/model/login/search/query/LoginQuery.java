package com.dereekb.gae.server.auth.model.login.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.DateQueryFieldParameter;

/**
 * Utility used for querying a {@link Login}.
 * 
 * @author dereekb
 *
 */
public class LoginQuery
        implements ConfigurableQueryParameters {

	public static final String DATE_FIELD = "date";

	private DateQueryFieldParameter date;

	public DateQueryFieldParameter getDate() {
		return this.date;
	}

	public void setDate(DateQueryFieldParameter date) {
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
			this.date = new DateQueryFieldParameter(DATE_FIELD, dateString);
		} else {
			this.date = null;
		}
	}

}
