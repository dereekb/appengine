package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.EventGroup;
import com.dereekb.gae.utilities.misc.keyed.IndexCoded;

/**
 * Common {@link EventGroup} values.
 *
 * @author dereekb
 *
 */
public enum CommonEventGroup implements IndexCoded, EventGroup {

	MODEL_EVENT(0, "model"),

	SYSTEM_EVENT(1, "system");

	public final int code;
	public final String eventGroupCode;

	private CommonEventGroup(final int code, final String eventGroupCode) {
		this.code = code;
		this.eventGroupCode = eventGroupCode;
	}

	// MARK: IndexCoded
	@Override
	public Integer getCode() {
		return this.code;
	}

	// MARK: EventGroup
	@Override
	public String getEventGroupCode() {
		return this.eventGroupCode;
	}

}
