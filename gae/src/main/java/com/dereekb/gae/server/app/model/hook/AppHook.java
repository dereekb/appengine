package com.dereekb.gae.server.app.model.hook;

import com.dereekb.gae.server.app.model.app.shared.impl.AbstractLongKeyedAppRelatedModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNull;

/**
 * {@link App} {@link WebHookEvent} subscription.
 *
 * @author dereekb
 *
 */
public class AppHook extends AbstractLongKeyedAppRelatedModel<AppHook> {

	private static final long serialVersionUID = 1L;

	/**
	 * Event group.
	 */
	@Index
	private String group;

	/**
	 * Event type.
	 */
	@Index
	private String event;

	/**
	 * Whether or not the hook is enabled.
	 */
	@Index
	@IgnoreSave({ IfNull.class })
	private Boolean enabled = true;

	/**
	 * API path to send the {@link WebHookEvent}.
	 * <p>
	 * Relative to the {@link App}'s api path.
	 */
	private String path;

	// TODO: Filters and other components for restricting the hooks caught by
	// this event.

	public AppHook() {
		super();
	}

	public void setEventDetails(EventType eventType) {
		this.setGroup(eventType.getEventGroupCode());
		this.setEvent(eventType.getEventTypeCode());
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	// ObjectifyModel
	@Override
	public Key<AppHook> getObjectifyKey() {
		return ObjectifyKeyUtility.createKey(AppHook.class, this.identifier);
	}

	@Override
	public String toString() {
		return "AppHook [group=" + this.group + ", event=" + this.event + ", path=" + this.path + ", identifier="
		        + this.identifier + ", app=" + this.app + ", date=" + this.date + "]";
	}

}
