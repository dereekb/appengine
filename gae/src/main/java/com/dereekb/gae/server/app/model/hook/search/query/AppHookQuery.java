package com.dereekb.gae.server.app.model.hook.search.query;

import java.util.Map;

import com.dereekb.gae.server.app.model.app.shared.search.query.AbstractAppRelatedModelQuery;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.impl.BooleanQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.StringQueryFieldParameter;

/**
 * Utility used for querying an {@link AppHook}.
 *
 * @author dereekb
 *
 */
public class AppHookQuery extends AbstractAppRelatedModelQuery {

	public static final String GROUP_FIELD = "group";
	public static final String EVENT_FIELD = "event";
	public static final String ENABLED_FIELD = "enabled";

	private StringQueryFieldParameter group;
	private StringQueryFieldParameter event;
	private BooleanQueryFieldParameter enabled;

	public AppHookQuery() {
		super();
	}

	public AppHookQuery(Map<String, String> parameters) throws IllegalArgumentException {
		super(parameters);
	}

	public void searchEventType(EventType eventType) {
		this.setGroup(eventType.getEventGroupCode());
		this.setEvent(eventType.getEventTypeCode());
	}

	public StringQueryFieldParameter getGroup() {
		return this.group;
	}

	public void setGroup(String group) throws IllegalArgumentException {
		this.group = StringQueryFieldParameter.make(GROUP_FIELD, group);
	}

	public void setGroup(StringQueryFieldParameter group) throws IllegalArgumentException {
		this.group = StringQueryFieldParameter.make(GROUP_FIELD, group);
	}

	public StringQueryFieldParameter getEvent() {
		return this.event;
	}

	public void setEvent(String event) throws IllegalArgumentException {
		this.event = StringQueryFieldParameter.make(EVENT_FIELD, event);
	}

	public void setEvent(StringQueryFieldParameter event) throws IllegalArgumentException {
		this.event = StringQueryFieldParameter.make(EVENT_FIELD, event);
	}

	public BooleanQueryFieldParameter getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = BooleanQueryFieldParameter.make(ENABLED_FIELD, enabled);
	}

	public void searchIsEnabled() {
		this.setEnabled(true);
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = BooleanQueryFieldParameter.make(ENABLED_FIELD, enabled);
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = super.getParameters();

		ParameterUtility.put(parameters, this.getGroup());
		ParameterUtility.put(parameters, this.getEvent());
		ParameterUtility.put(parameters, this.getEnabled());

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		super.setParameters(parameters);
		this.setGroup(parameters.get(GROUP_FIELD));
		this.setEvent(parameters.get(EVENT_FIELD));
		this.setEnabled(parameters.get(ENABLED_FIELD));
	}

}
