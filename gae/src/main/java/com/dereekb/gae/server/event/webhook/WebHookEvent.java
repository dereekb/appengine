package com.dereekb.gae.server.event.webhook;

import com.dereekb.gae.server.event.event.Event;

/**
 * Web hook event used for sending events to other servers.
 *
 * @author dereekb
 *
 */
public interface WebHookEvent {

	public Event getEvent();

}
