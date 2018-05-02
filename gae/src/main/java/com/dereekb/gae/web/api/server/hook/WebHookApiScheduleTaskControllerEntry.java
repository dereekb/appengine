package com.dereekb.gae.web.api.server.hook;

import java.io.IOException;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;
import com.dereekb.gae.web.api.server.schedule.impl.AbstractSingleTaskApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;
import com.dereekb.gae.web.taskqueue.server.webhook.TaskQueueWebHookController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ApiScheduleTaskControllerEntry} for receiving web hook events and
 * building a task for the {@link TaskQueueWebHookController}.
 *
 * @author dereekbs
 *
 */
public class WebHookApiScheduleTaskControllerEntry extends AbstractSingleTaskApiScheduleTaskControllerEntry {

	/**
	 * Key used by this entry.
	 */
	public static final String SCHEDULE_TASK_ENTRY_KEY = "webhook";

	private ObjectMapper mapper = ObjectMapperUtilityBuilderImpl.MAPPER;

	public ObjectMapper getMapper() {
		return this.mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		if (mapper == null) {
			throw new IllegalArgumentException("mapper cannot be null.");
		}

		this.mapper = mapper;
	}

	// MARK: AbstractSingleTaskApiScheduleTaskControllerEntry
	@Override
	public TaskRequest makeTaskRequest(ApiScheduleTaskRequest request)
	        throws MultiKeyedInvalidAttributeException,
	            KeyedInvalidAttributeException,
	            IllegalArgumentException {

		JsonNode requestData = request.getData();

		if (requestData == null) {
			throw new KeyedInvalidAttributeException(ModelKey.nullKey(), "data", null, "Request data was not present.");
		}

		// TODO: Consider validating the events before passing them on.

		try {
			TaskRequestImpl taskRequest = new TaskRequestImpl(TaskQueueWebHookController.WEBHOOK_EVENT_PATH);

			String jsonEventData = this.mapper.writeValueAsString(requestData);
			taskRequest.setRequestData(jsonEventData);

			return taskRequest;
		} catch (IOException e) {
			throw new KeyedInvalidAttributeException(ModelKey.nullKey(), "data", requestData.toString(),
			        "Request data failed to be serialized.");
		}
	}

	@Override
	public String toString() {
		return "HookApiScheduleTaskControllerEntry [mapper=" + this.mapper + "]";
	}

}
