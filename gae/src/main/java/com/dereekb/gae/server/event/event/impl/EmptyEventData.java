package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.EventData;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * Empty {@link EventData} implementation.
 *
 * @author dereekb
 *
 */
public class EmptyEventData extends AbstractEventData {

	public static final String EMPTY_DATA_TYPE = "none";
	public static final EmptyEventData SINGLETON = new EmptyEventData();

	public EmptyEventData() {
		this(EMPTY_DATA_TYPE);
	}

	public EmptyEventData(String eventDataType) {
		super(eventDataType);
	}

	public static EventData make() {
		return SINGLETON;
	}

	// MARK: EventData
	@Override
	public ApiResponseData getWebSafeData(Parameters parameters) {
		return null;
	}

	@Override
	public String toString() {
		return "EmptyEventData []";
	}

}
