package com.dereekb.gae.server.event.webhook.impl;

import com.dereekb.gae.server.event.event.EventData;
import com.dereekb.gae.server.event.event.impl.AbstractEventDataImpl;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link EventData} implementation with arbitrary JSON.
 *
 * @author dereekb
 *
 */
public class JsonEventDataImpl extends AbstractEventDataImpl {

	public static final String EVENT_DATA_TYPE = "json";

	private JsonNode json;

	public JsonEventDataImpl() {
		super(EVENT_DATA_TYPE);
	}

	public JsonNode getJson() {
		return this.json;
	}

	public void setJson(JsonNode json) {
		if (json == null) {
			throw new IllegalArgumentException("json cannot be null.");
		}

		this.json = json;
	}

	@Override
	public String toString() {
		return "JsonEventDataImpl [json=" + this.json + "]";
	}

}
