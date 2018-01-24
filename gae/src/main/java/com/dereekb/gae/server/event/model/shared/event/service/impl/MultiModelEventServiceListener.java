package com.dereekb.gae.server.event.model.shared.event.service.impl;

import org.springframework.ui.Model;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.impl.CommonEventGroup;
import com.dereekb.gae.server.event.event.service.EventServiceListener;

/**
 * {@link EventServiceListener} implementation for {@link Model}.
 *
 * @author dereekb
 *
 */
public class MultiModelEventServiceListener
        implements EventServiceListener {

	private String modelEventGroup = CommonEventGroup.MODEL_EVENT.getEventGroupCode();

	public String getModelEventGroup() {
		return this.modelEventGroup;
	}

	public void setModelEventGroup(String modelEventGroup) {
		if (modelEventGroup == null) {
			throw new IllegalArgumentException("modelEventGroup cannot be null.");
		}

		this.modelEventGroup = modelEventGroup;
	}

	// MARK: EventServiceListener
	@Override
	public void handleEvent(Event event) {
		EventType type = event.getEventType();
		String eventGroup = type.getEventGroupCode();

		if (eventGroup.equals(this.modelEventGroup)) {

		}
	}

}
