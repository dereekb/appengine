package com.dereekb.gae.server.event.webhook.impl;

import javax.validation.constraints.NotNull;

import com.dereekb.gae.server.event.event.EventData;
import com.dereekb.gae.server.event.webhook.WebHookEventData;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Abstract {@link WebHookEventData} that wraps the {@link EventData}'s type.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebHookEventDataImpl
        implements WebHookEventData {

	public static final String TYPE_FIELD = "type";

	@NotNull
	private String type;

	public WebHookEventDataImpl() {}

	public WebHookEventDataImpl(String type) {
		this.setType(type);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// MARK: EventData
	@JsonIgnore
	@Override
	public String getEventDataType() {
		return this.type;
	}

	@JsonIgnore
	@Override
	public String keyValue() {
		return this.type;
	}

	@JsonIgnore
	@Override
	public JsonNode getJsonNode() {
		return ObjectMapperUtilityBuilderImpl.MAPPER.valueToTree(this);
	}

	@Override
	public String toString() {
		return "AbstractWebHookEventDataImpl [type=" + this.type + "]";
	}

}
