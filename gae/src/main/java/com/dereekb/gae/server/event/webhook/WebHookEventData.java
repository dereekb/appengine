package com.dereekb.gae.server.event.webhook;

import com.dereekb.gae.server.event.event.EventData;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link WebHookEvent} data.
 *
 * @author dereekb
 *
 */
public interface WebHookEventData
        extends EventData {

	/**
	 * Returns the root JSON node for this event data.
	 *
	 * @return {@link JsonNode}. Never {@code null}.
	 */
	public JsonNode getJsonNode();

}
