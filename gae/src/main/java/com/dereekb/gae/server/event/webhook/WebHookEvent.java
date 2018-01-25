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
	 * Returns the root JSON node for this event.
	 *
	 * @return {@link JsonNode}. Never {@code null}.
	 */
	public JsonNode getJsonNode();

	/**
	 * Returns the data for this web hook event, if available.
	 *
	 * @return {@link WebHookEventData}, or {@code null} if none available.
	 */
	public WebHookEventData getEventData();

}
