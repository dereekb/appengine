package com.dereekb.gae.web.api.server.hook;

import java.io.IOException;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;
import com.dereekb.gae.web.api.server.schedule.impl.AbstractSingleTaskApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;
import com.dereekb.gae.web.taskqueue.server.hook.WebHookTaskQueueTaskControllerEntry;
import com.dereekb.gae.web.taskqueue.server.task.TaskQueueTaskController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link AbstractSingleTaskApiScheduleTaskControllerEntry} for recieving
 * webhook events and building a task for the
 * {@link WebHookTaskQueueTaskControllerEntry}.
 *
 * @author dereekb
 *
 */
public class HookApiScheduleTaskControllerEntry extends AbstractSingleTaskApiScheduleTaskControllerEntry {

	public static final String ENTRY_KEY = "webhook";

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
			TaskRequestImpl taskRequest = new TaskRequestImpl();

			String path = TaskQueueTaskController.pathForTask(WebHookTaskQueueTaskControllerEntry.TASK_ENTRY_KEY);
			taskRequest.setPath(path);

			String jsonEventData = this.mapper.writeValueAsString(requestData);

			KeyedEncodedParameter eventDataParam = new KeyedEncodedParameterImpl(
			        WebHookTaskQueueTaskControllerEntry.WEB_HOOK_DATA_PARAM, jsonEventData);
			taskRequest.replaceParameter(eventDataParam);

			return taskRequest;
		} catch (IOException e) {
			throw new KeyedInvalidAttributeException(ModelKey.nullKey(), "data", requestData.toString(),
			        "Request data failed to be serialized.");
		}
	}

}
