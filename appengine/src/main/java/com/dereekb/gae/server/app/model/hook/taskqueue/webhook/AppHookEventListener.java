package com.dereekb.gae.server.app.model.hook.taskqueue.webhook;

import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.app.model.hook.link.AppHookLinkSystemBuilderEntry;
import com.dereekb.gae.server.app.model.hook.search.query.AppHookQuery;
import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.service.EventServiceListener;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.service.WebHookEventSerializer;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link EventServiceListener} that listens for events then passes them to the
 * Task Queue to be broadcast to {@link AppHook} values that match the query.
 *
 * @author dereekb
 *
 */
public class AppHookEventListener
        implements EventServiceListener {

	private TaskScheduler scheduler;
	private WebHookEventSerializer eventSerializer;

	public AppHookEventListener(TaskScheduler scheduler, WebHookEventSerializer eventSerializer) {
		this.setScheduler(scheduler);
		this.setEventSerializer(eventSerializer);
	}

	public TaskScheduler getScheduler() {
		return this.scheduler;
	}

	public void setScheduler(TaskScheduler scheduler) {
		if (scheduler == null) {
			throw new IllegalArgumentException("scheduler cannot be null.");
		}

		this.scheduler = scheduler;
	}

	public WebHookEventSerializer getEventSerializer() {
		return this.eventSerializer;
	}

	public void setEventSerializer(WebHookEventSerializer eventSerializer) {
		if (eventSerializer == null) {
			throw new IllegalArgumentException("eventSerializer cannot be null.");
		}

		this.eventSerializer = eventSerializer;
	}

	// MARK: EventServiceListener
	@Override
	public void handleEvent(Event event) {
		WebHookEvent webHookEvent = this.eventSerializer.convertSingle(event);

		// TODO: Consider scheduling twice with different query parameters. (null and with
		// scope set?)

		TaskRequest request = this.makeTaskRequest(webHookEvent);
		this.scheduler.schedule(request);
	}

	private TaskRequest makeTaskRequest(WebHookEvent webHookEvent) {

		// Make Query
		AppHookQuery query = new AppHookQuery();

		query.searchEventType(webHookEvent.getEventType());
		query.setEnabled(true);

		// Create Task With Query
		String taskPath = TaskQueueIterateController.pathForIterateTask(AppHookLinkSystemBuilderEntry.LINK_MODEL_TYPE,
		        ScheduleAppWebHookSendTaskFactory.TASK_KEY);
		TaskRequestImpl taskRequest = new TaskRequestImpl(taskPath);

		taskRequest.setParameters(query.getParameters());

		JsonNode jsonNode = webHookEvent.getJsonNode();
		String json = ObjectMapperUtilityBuilderImpl.SINGLETON.make().writeJsonString(jsonNode);
		KeyedEncodedParameter eventDataParameter = new KeyedEncodedParameterImpl(
		        ScheduleAppWebHookSendTaskFactory.EVENT_DATA_KEY, json);
		taskRequest.replaceParameter(eventDataParameter);

		return taskRequest;
	}

	@Override
	public String toString() {
		return "AppHookEventListener [scheduler=" + this.scheduler + ", eventSerializer=" + this.eventSerializer + "]";
	}

}
