package com.dereekb.gae.server.event.model.shared.event.service.listener.impl;

import java.util.Map;

import com.dereekb.gae.server.event.event.service.EventServiceListener;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEvent;
import com.dereekb.gae.server.event.model.shared.event.service.listener.ModelKeyEventServiceListener;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveEntryContainer;
import com.dereekb.gae.utilities.collections.map.impl.CaseInsensitiveEntryContainerImpl;

/**
 * {@link EventServiceListener} implementation for multiple model types.
 *
 * @author dereekb
 *
 */
public class MultiTypeModelKeyEventServiceListener extends AbstractModelKeyEventServiceListenerImpl {

	private CaseInsensitiveEntryContainer<ModelKeyEventServiceListener> map;

	public MultiTypeModelKeyEventServiceListener() {
		super();
	}

	public MultiTypeModelKeyEventServiceListener(Map<String, ModelKeyEventServiceListener> map) {
		this.setMap(map);
	}

	public CaseInsensitiveEntryContainer<ModelKeyEventServiceListener> getMap() {
		return this.map;
	}

	public void setMap(Map<String, ModelKeyEventServiceListener> map) {
		this.setMap(new CaseInsensitiveEntryContainerImpl<ModelKeyEventServiceListener>(map));
	}

	public void setMap(CaseInsensitiveEntryContainer<ModelKeyEventServiceListener> map) {
		if (map == null) {
			throw new IllegalArgumentException("map cannot be null.");
		}

		this.map = map;
	}

	// MARK: EventServiceListener
	@Override
	public void handleModelKeyEvent(ModelKeyEvent event) {
		String modelType = event.getModelType();

		ModelKeyEventServiceListener listener = null;

		try {
			listener = this.map.getEntryForType(modelType);
		} catch (RuntimeException e) {
			return;	// Do nothing.
		}

		listener.handleModelKeyEvent(event);
	}

	@Override
	public String toString() {
		return "MultiTypeModelKeyEventServiceListener [map=" + this.map + "]";
	}

}
