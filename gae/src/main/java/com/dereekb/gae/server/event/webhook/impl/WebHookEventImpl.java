package com.dereekb.gae.server.event.webhook.impl;

import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.impl.EventTypeImpl;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link WebHookEvent} implementation meant for external JSON
 * serialization.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebHookEventImpl
        implements WebHookEvent {

	public static final String GROUP_FIELD = "group";
	public static final String TYPE_FIELD = "type";
	public static final String DATA_FIELD = "data";

	private String group;
	private String type;
	private Object data;

	public WebHookEventImpl(EventType eventType) {
		this(eventType.getEventGroupCode(), eventType.getEventTypeCode(), null);
	}

	public WebHookEventImpl(EventType eventType, Object data) {
		this(eventType.getEventGroupCode(), eventType.getEventTypeCode(), data);
	}

	public WebHookEventImpl(String group, String type, Object data) {
		super();
		this.setGroup(group);
		this.setType(type);
		this.setData(data);
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		if (group == null) {
			throw new IllegalArgumentException("group cannot be null.");
		}

		this.group = group;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		if (type == null) {
			throw new IllegalArgumentException("type cannot be null.");
		}

		this.type = type;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	// MARK: WebHookEvent
	@JsonIgnore
	@Override
	public EventType getEventType() {
		return new EventTypeImpl(this.group, this.type);
	}

	@JsonIgnore
	@Override
	public JsonNode getJsonNode() {
		return new ObjectMapper().valueToTree(this);
	}

	@Override
	public String toString() {
		return "WebHookEventImpl [group=" + this.group + ", type=" + this.type + ", data=" + this.data + "]";
	}

}
