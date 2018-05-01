package com.dereekb.gae.web.taskqueue.server.webhook;

import java.io.IOException;
import java.util.Map;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.service.EventService;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.impl.JsonWebHookEventImpl;
import com.dereekb.gae.server.event.webhook.service.WebHookEventDeserializer;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.dereekb.gae.web.api.server.hook.HookApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.taskqueue.server.task.TaskQueueTaskControllerEntry;
import com.dereekb.gae.web.taskqueue.server.task.TaskQueueTaskControllerRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link TaskQueueTaskControllerEntry} for receiving remote
 * {@link WebHookEvent} tasks and submitting them to the local event service.
 *
 * @author dereekb
 *
 * @deprecated Use {@link TaskQueueWebHookController} instead.
 *
 * @see {@link HookApiScheduleTaskControllerEntry} for scheduling tasks through
 *      the REST API.
 */
@Deprecated
public class WebHookTaskQueueTaskControllerEntry
        implements TaskQueueTaskControllerEntry {

	public static final String TASK_ENTRY_KEY = "webhook";
	public static final String WEB_HOOK_DATA_PARAM = "eventdata";

	private ObjectMapper mapper = ObjectMapperUtilityBuilderImpl.MAPPER;

	private EventService eventService;
	private WebHookEventDeserializer eventDeserializer;

	public WebHookTaskQueueTaskControllerEntry(EventService eventService, WebHookEventDeserializer eventDeserializer) {
		this.setEventService(eventService);
		this.setEventDeserializer(eventDeserializer);
	}

	public ObjectMapper getMapper() {
		return this.mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		if (mapper == null) {
			throw new IllegalArgumentException("mapper cannot be null.");
		}

		this.mapper = mapper;
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

	// MARK: TaskQueueTaskControllerEntry
	@Override
	public void performTask(TaskQueueTaskControllerRequest request) throws RuntimeException {
		Map<String, String> parameters = request.getParameters();

		try {
			String eventData = parameters.get(WEB_HOOK_DATA_PARAM);
			JsonNode jsonEventData = this.mapper.readTree(eventData);
			WebHookEvent webHookEvent = new JsonWebHookEventImpl(jsonEventData);
			Event event = this.eventDeserializer.convertSingle(webHookEvent);
			this.eventService.submitEvent(event);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return "WebHookTaskQueueTaskControllerEntry [mapper=" + this.mapper + ", eventService=" + this.eventService
		        + ", eventDeserializer=" + this.eventDeserializer + "]";
	}

}
