package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.EventData;

/**
 * Empty {@link EventData} implementation.
 *
 * @author dereekb
 *
 */
public class EmptyEventData extends AbstractEventDataImpl {

	public static final String EVENT_DATA_TYPE = "empty";
	public static final EmptyEventData SINGLETON = new EmptyEventData();

	public EmptyEventData() {
		super(EVENT_DATA_TYPE);
	}

	public static EventData make() {
		return SINGLETON;
	}

	// MARK: EventData
	@Override
	public String toString() {
		return "EmptyEventData []";
	}

}
