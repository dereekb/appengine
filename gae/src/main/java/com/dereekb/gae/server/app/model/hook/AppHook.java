package com.dereekb.gae.server.app.model.hook;

import com.dereekb.gae.server.app.model.app.shared.impl.AbstractLongKeyedAppRelatedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyGenerationType;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNull;
import com.googlecode.objectify.condition.IfZero;

/**
 * {@link App} {@link WebHookEvent} subscription.
 *
 * @author dereekb
 *
 */
@ModelKeyInfo(value = ModelKeyType.NUMBER, generation = ModelKeyGenerationType.AUTOMATIC)
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

	/**
	 * Returns the number of send failures.
	 */
	@Index
	@IgnoreSave({ IfZero.class })
	private Integer failures = 0;

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

	public Integer getFailures() {
		return this.failures;
	}

	public void setFailures(Integer failures) {
		this.failures = failures;
	}

	// ObjectifyModel
	@Override
	public Key<AppHook> getObjectifyKey() {
		return ObjectifyKeyUtility.createKey(AppHook.class, this.identifier);
	}

	@Override
	public String toString() {
		return "AppHook [group=" + this.group + ", event=" + this.event + ", enabled=" + this.enabled + ", path="
		        + this.path + ", failures=" + this.failures + "]";
	}

}
