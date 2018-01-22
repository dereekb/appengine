package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.EventGroup;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.utilities.misc.keyed.IndexCoded;

/**
 * Common {@link EventGroup} values.
 *
 * @author dereekb
 *
 */
public enum CommonModelEventType implements IndexCoded, EventType {

	CREATED(0, "created"),

	UPDATED(1, "updated"),

	DELETED(2, "deleted");

	public final int code;
	public final String eventTypeCode;

	private CommonModelEventType(final int code, final String eventTypeCode) {
		this.code = code;
		this.eventTypeCode = eventTypeCode;
	}

	// MARK: IndexCoded
	@Override
	public Integer getCode() {
		return this.code;
	}

	// MARK: EventGroup
	@Override
	public String getEventGroupCode() {
		return CommonEventGroup.MODEL_EVENT.getEventGroupCode();
	}

	// MARK: EventType
	@Override
	public String getEventTypeCode() {
		return this.eventTypeCode;
	}

}
