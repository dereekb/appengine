package com.dereekb.gae.web.taskqueue.server.webhook.impl;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.service.EventService;
import com.dereekb.gae.server.event.event.service.exception.EventServiceException;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.listener.WebHookEventSubmitter;
import com.dereekb.gae.server.event.webhook.listener.exception.WebHookEventSubmitException;
import com.dereekb.gae.server.event.webhook.service.WebHookEventDeserializer;
import com.dereekb.gae.web.taskqueue.server.webhook.TaskQueueWebHookControllerDelegate;

/**
 * {@link TaskQueueWebHookControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class TaskQueueWebHookControllerDelegateImpl
        implements TaskQueueWebHookControllerDelegate {

	private EventService eventService;
	private WebHookEventDeserializer eventDeserializer;

	private WebHookEventSubmitter eventSubmitter;

	public TaskQueueWebHookControllerDelegateImpl(EventService eventService,
	        WebHookEventDeserializer eventDeserializer,
	        WebHookEventSubmitter eventSubmitter) {
		this.setEventService(eventService);
		this.setEventDeserializer(eventDeserializer);
		this.setEventSubmitter(eventSubmitter);
	}

	public EventService getEventService() {
		return this.eventService;
	}

	public void setEventService(EventService eventService) {
		if (eventService == null) {
			throw new IllegalArgumentException("eventService cannot be null.");
		}

		this.eventService = eventService;
	}

	public WebHookEventDeserializer getEventDeserializer() {
		return this.eventDeserializer;
	}

	public void setEventDeserializer(WebHookEventDeserializer eventDeserializer) {
		if (eventDeserializer == null) {
			throw new IllegalArgumentException("eventDeserializer cannot be null.");
		}

		this.eventDeserializer = eventDeserializer;
	}

	public WebHookEventSubmitter getEventSubmitter() {
		return this.eventSubmitter;
	}

	public void setEventSubmitter(WebHookEventSubmitter eventSubmitter) {
		if (eventSubmitter == null) {
			throw new IllegalArgumentException("eventSubmitter cannot be null.");
		}

		this.eventSubmitter = eventSubmitter;
	}

	// MARK: TaskQueueWebHookControllerDelegate
	@Override
	public void processEvent(WebHookEvent webHookEvent) throws EventServiceException {
		Event event = this.eventDeserializer.convertSingle(webHookEvent);
		this.eventService.submitEvent(event);
	}

	@Override
	public void resubmitEvent(WebHookEvent webHookEvent) throws WebHookEventSubmitException {
		this.eventSubmitter.submitEvent(webHookEvent);
	}

	@Override
	public String toString() {
		return "TaskQueueWebHookControllerDelegateImpl [eventService=" + this.eventService + ", eventDeserializer="
		        + this.eventDeserializer + ", eventSubmitter=" + this.eventSubmitter + "]";
	}

}
