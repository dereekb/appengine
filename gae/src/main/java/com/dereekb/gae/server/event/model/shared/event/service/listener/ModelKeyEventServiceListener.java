package com.dereekb.gae.server.event.model.shared.event.service.listener;

import com.dereekb.gae.server.event.model.shared.event.ModelKeyEvent;

/**
 * Event listener that listens for {@link ModelKeyEvent} instances.
 *
 * @author dereekb
 *
 */
public interface ModelKeyEventServiceListener {

	/**
	 * Handles the input event.
	 *
	 * @param event
	 *            {@link ModelKeyEvent}. Never {@code null}.
	 */
	public void handleModelKeyEvent(ModelKeyEvent event);

}
