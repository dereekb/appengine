package com.dereekb.gae.server.event.event.impl;

import java.util.Set;

import com.dereekb.gae.server.event.event.EventData;

/**
 * Empty {@link EventData} implementation.
 *
 * @author dereekb
 *
 */
public class EmptyEventData
        implements EventData {

	public static final EmptyEventData SINGLETON = new EmptyEventData();

	public static EventData make() {
		return SINGLETON;
	}

	// MARK: EventData
	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
