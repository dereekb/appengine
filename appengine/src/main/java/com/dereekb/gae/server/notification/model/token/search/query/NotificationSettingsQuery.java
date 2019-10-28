package com.dereekb.gae.server.notification.model.token.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.notification.model.token.NotificationSettings;

/**
 * Utility used for querying a {@link NotificationSettings}.
 *
 * @author dereekb
 *
 */
public class NotificationSettingsQuery {

	// No fields to query on.

	public NotificationSettingsQuery() {
		super();
	}

	public NotificationSettingsQuery(Map<String, String> parameters) throws IllegalArgumentException {
		this.setParameters(parameters);
	}

	// MARK: ConfigurableQueryParameters
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		// Do nothing.
	}

}
