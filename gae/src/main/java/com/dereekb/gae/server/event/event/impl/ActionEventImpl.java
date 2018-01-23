package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.ActionEvent;
import com.dereekb.gae.server.event.event.EventData;
import com.dereekb.gae.server.event.event.EventType;

@Deprecated
public class ActionEventImpl extends EventImpl
        implements ActionEvent {

	private String eventAction;

	public ActionEventImpl(EventType eventType, EventData eventData) {
		super(eventType, eventData);
	}

	@Override
	public String getEventAction() {
		return this.eventAction;
	}

	public void setEventAction(String eventAction) {
		if (eventAction == null) {
			throw new IllegalArgumentException("eventAction cannot be null.");
		}

		this.eventAction = eventAction;
	}

}
