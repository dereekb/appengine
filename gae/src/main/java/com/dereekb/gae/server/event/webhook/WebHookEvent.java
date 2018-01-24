package com.dereekb.gae.server.event.webhook;

import com.dereekb.gae.server.event.event.BasicEvent;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Web hook event used for sending events to other servers. Wraps the data for
 * an event.
 * </p>
 * All data in this event will be used for outside sources, so
 * implementations should keep this in mind.
 *
 * @author dereekb
 *
 */
public interface WebHookEvent
        extends BasicEvent {

	/**
	 * Returns the root JSON node for the event.
	 *
	 * @return {@link JsonNode}. Never {@code null}.
	 */
	public JsonNode getJsonNode();

}
