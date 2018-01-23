package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.EventData;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * Abstract {@link EventData} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractEventData
        implements EventData {

	private String eventDataType;

	public AbstractEventData(String eventDataType) {
		this.setEventDataType(eventDataType);
	}

	// MARK: EventData
	@Override
	public String getEventDataType() {
		return this.eventDataType;
	}

	public void setEventDataType(String eventDataType) {
		if (eventDataType == null) {
			throw new IllegalArgumentException("eventDataType cannot be null.");
		}

		this.eventDataType = eventDataType;
	}

	@Override
	public ApiResponseData getWebSafeData() {
		return this.getWebSafeData(null);
	}

	@Override
	public abstract ApiResponseData getWebSafeData(Parameters parameters);

	@Override
	public String keyValue() {
		return this.eventDataType;
	}

	@Override
	public String toString() {
		return "AbstractEventData [eventDataType=" + this.eventDataType + "]";
	}

}
