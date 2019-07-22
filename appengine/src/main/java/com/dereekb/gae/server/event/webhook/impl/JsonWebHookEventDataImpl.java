package com.dereekb.gae.server.event.webhook.impl;

import com.dereekb.gae.server.event.webhook.WebHookEventData;
import com.dereekb.gae.utilities.data.StringUtility;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link WebHookEventData} implementation that is built from a
 * {@link JsonNode}.
 *
 * @author dereekb
 *
 */
public class JsonWebHookEventDataImpl extends WebHookEventDataImpl {

	private JsonNode jsonNode;

	public JsonWebHookEventDataImpl(JsonNode dataRoot) throws IllegalArgumentException {
		this.setJsonNode(dataRoot);

		JsonNode type = this.jsonNode.get(WebHookEventDataImpl.TYPE_FIELD);

		if (type == null) {
			throw new IllegalArgumentException("Json is missing 'type' field.");
		} else {
			String typeText = type.asText();

			if (StringUtility.isEmptyString(typeText)) {
				throw new IllegalArgumentException("Data type string cannot be empty.");
			}

			this.setType(typeText);
		}
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
		return "JsonWebHookEventDataImpl [jsonNode=" + this.jsonNode + ", getEventDataType()=" + this.getEventDataType()
		        + "]";
	}

}
