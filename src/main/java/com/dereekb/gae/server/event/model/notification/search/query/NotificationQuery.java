package com.dereekb.gae.server.event.model.notification.search.query;

import java.util.Map;

import com.dereekb.gae.server.auth.security.model.query.impl.AbstractOwnedDateModelQuery;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.impl.BooleanQueryFieldParameter;

/**
 * Utility used for querying a {@link Notification}.
 * 
 * @author dereekb
 *
 */
public class NotificationQuery extends AbstractOwnedDateModelQuery {

	public static final String READ_FIELD = "read";

	private BooleanQueryFieldParameter read;

	public NotificationQuery() {
		super();
	}

	public NotificationQuery(Map<String, String> parameters) throws IllegalArgumentException {
		super(parameters);
	}

	public BooleanQueryFieldParameter getRead() {
		return this.read;
	}

	public void setRead(String read) {
		this.read = BooleanQueryFieldParameter.make(READ_FIELD, read);
	}

	public void searchIsNotRead() {
		this.setRead(false);
	}

	public void setRead(Boolean read) {
		this.read = BooleanQueryFieldParameter.make(READ_FIELD, read);
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = super.getParameters();
		ParameterUtility.put(parameters, this.read);
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		this.setRead(parameters.get(READ_FIELD));
		super.setParameters(parameters);
	}

}
