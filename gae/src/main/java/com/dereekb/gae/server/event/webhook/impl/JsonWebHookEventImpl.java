package com.dereekb.gae.server.event.webhook.impl;

import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.impl.EventTypeImpl;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Incoming event that was deserialized to a json node.
 *
 * @author dereekb
 *
 */
public class JsonWebHookEventImpl
        implements WebHookEvent {

	private transient EventType eventType;
	private JsonNode jsonNode;

	public JsonWebHookEventImpl(JsonNode jsonNode) throws IllegalArgumentException {
		this.setJsonNode(jsonNode);
		this.assertIsValid();
	}

	protected void assertIsValid() throws IllegalArgumentException {
		if (!(this.jsonNode.has(WebHookEventImpl.GROUP_FIELD) && this.jsonNode.has(WebHookEventImpl.TYPE_FIELD))) {
			throw new IllegalArgumentException("Json is missing group or type field.");
		}
	}

	// MARK: WebHookEvent
	@Override
	public EventType getEventType() {
		if (this.eventType == null) {
			this.eventType = this.deserializeEventType();
		}

		return this.eventType;
	}

	private EventType deserializeEventType() {
		String group = this.jsonNode.get(WebHookEventImpl.GROUP_FIELD).asText();
		String type = this.jsonNode.get(WebHookEventImpl.TYPE_FIELD).asText();
		return new EventTypeImpl(group, type);
	}

	@Override
	public JsonNode getJsonNode() {
		return this.jsonNode;
	}

	public void setJsonNode(JsonNode jsonNode) {
		if (jsonNode == null) {
			throw new IllegalArgumentException("jsonNode cannot be null.");
		}

		this.jsonNode = jsonNode;
	}

	@Override
	public String toString() {
		return "DeserializedWebHookEventImpl [eventType=" + this.eventType + ", jsonNode=" + this.jsonNode + "]";
	}

}
