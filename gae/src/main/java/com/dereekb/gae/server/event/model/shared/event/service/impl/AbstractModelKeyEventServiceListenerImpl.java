package com.dereekb.gae.server.event.model.shared.event.service.impl;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventGroup;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.impl.CommonEventGroup;
import com.dereekb.gae.server.event.event.service.impl.AbstractFilteredEventServiceListenerImpl;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEvent;
import com.dereekb.gae.server.event.model.shared.event.service.ModelKeyEventServiceListener;
import com.dereekb.gae.utilities.filters.Filter;

/**
 * Abstract {@link ModelKeyEventServiceListener} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractModelKeyEventServiceListenerImpl extends AbstractFilteredEventServiceListenerImpl<ModelKeyEvent>
        implements ModelKeyEventServiceListener {

	public AbstractModelKeyEventServiceListenerImpl() {
		this(CommonEventGroup.MODEL_EVENT);
	}

	public AbstractModelKeyEventServiceListenerImpl(EventGroup eventGroup) {
		super(ModelKeyEvent.class, eventGroup);
	}

	public AbstractModelKeyEventServiceListenerImpl(EventType eventType) {
		super(ModelKeyEvent.class, eventType);
	}

	public AbstractModelKeyEventServiceListenerImpl(Filter<Event> filter) {
		super(ModelKeyEvent.class, filter);
	}

	// MARK: AbstractFilteredEventServiceListenerImpl
	@Override
	public final void handleCastEvent(ModelKeyEvent event) {
		this.handleModelKeyEvent(event);;
	}

	@Override
	public abstract void handleModelKeyEvent(ModelKeyEvent event);

}
