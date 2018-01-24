package com.dereekb.gae.server.event.event.service.impl;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.service.EventServiceListener;

/**
 * Abstract {@link EventServiceListener} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractEventServiceListenerImpl
        implements EventServiceListener {

	// MARK: EventServiceListener
	@Override
	public final void handleEvent(Event event) {
		this.shouldReactToEvent(event);
	}

	protected abstract boolean shouldReactToEvent(Event event);

}
