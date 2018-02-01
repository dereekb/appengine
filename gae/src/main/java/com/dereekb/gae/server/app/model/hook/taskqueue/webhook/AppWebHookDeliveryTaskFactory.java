package com.dereekb.gae.server.app.model.hook.taskqueue.webhook;

import java.io.IOException;
import java.util.List;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskRequest;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskService;
import com.dereekb.gae.client.api.server.schedule.impl.ClientScheduleTaskRequestImpl;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedGetter;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.misc.path.PathUtility;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.api.server.hook.HookApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.server.schedule.impl.ApiScheduleTaskRequestImpl;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateTaskFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.Key;

/**
 * {@link TaskQueueIterateTaskFactory} implementation for {@link AppHook} that
 * delivers the scheduled events.
 *
 * @author dereekb
 *
 * @see ScheduleAppWebHookSendTaskFactory
 */
public class AppWebHookDeliveryTaskFactory
        implements TaskQueueIterateTaskFactory<AppHook> {

	public static final String TASK_KEY = "sendhookevent";
	public static final String EVENT_DATA_KEY = "eventdata";

	private ObjectifyKeyedGetter<App> appGetter;
	private ClientScheduleTaskService scheduleService;

	private String scheduleTaskName = HookApiScheduleTaskControllerEntry.ENTRY_KEY;

	private ObjectMapper mapper = new ObjectMapper();

	public ObjectifyKeyedGetter<App> getAppGetter() {
		return this.appGetter;
	}

	public void setAppGetter(ObjectifyKeyedGetter<App> appGetter) {
		if (appGetter == null) {
			throw new IllegalArgumentException("appGetter cannot be null.");
		}

		this.appGetter = appGetter;
	}

	public ClientScheduleTaskService getScheduleService() {
		return this.scheduleService;
	}

	public void setScheduleService(ClientScheduleTaskService scheduleService) {
		if (scheduleService == null) {
			throw new IllegalArgumentException("scheduleService cannot be null.");
		}

		this.scheduleService = scheduleService;
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

	// MARK: TaskQueueIterateTaskFactory
	@Override
	public Task<ModelKeyListAccessor<AppHook>> makeTask(IterateTaskInput input) throws FactoryMakeFailureException {
		try {
			return new ScheduledAppTaskInstance(input);
		} catch (IOException e) {
			throw new FactoryMakeFailureException("Failed to serialized event data.", e);
		}
	}

	private class ScheduledAppTaskInstance
	        implements Task<ModelKeyListAccessor<AppHook>> {

		private final IterateTaskInput taskInput;
		private transient JsonNode eventData;

		public ScheduledAppTaskInstance(IterateTaskInput taskInput) throws IOException {
			this.taskInput = taskInput;

			String eventDataString = taskInput.getParameters().get(EVENT_DATA_KEY);
			this.eventData = AppWebHookDeliveryTaskFactory.this.mapper.readTree(eventDataString);
		}

		// MARK: Task
		@Override
		public void doTask(ModelKeyListAccessor<AppHook> input) throws FailedTaskException {
			List<AppHook> models = input.getModels();

			for (AppHook model : models) {
				this.deliverEvent(model);
			}
		}

		private void deliverEvent(AppHook model) throws FailedTaskException {
			Key<App> appKey = model.getApp();
			App app = AppWebHookDeliveryTaskFactory.this.appGetter.get(appKey);

			if (app == null) {
				// Don't deliver the event if the app is deleted/unavailable.
				return;
			}

			ClientScheduleTaskRequest request = this.buildDeliveryRequest(app, model);
			ClientRequestSecurity security = null;

			switch (app.getAppLoginSecurityLevel()) {
				case APP:
					// TODO: If a normal app, send some sort of validation with
					// the request that the remote server can use to verify the
					// sender.
					break;
				case SYSTEM:
					// Send current system's credentials.
					security = ClientRequestSecurityImpl.systemSecurity();
					break;
			}

			try {
				AppWebHookDeliveryTaskFactory.this.scheduleService.scheduleTask(request, security);
			} catch (ClientRequestFailureException e) {
				throw new FailedTaskException(e);
			}
		}

		private ClientScheduleTaskRequest buildDeliveryRequest(App app,
		                                                       AppHook model) {

			String serverPath = app.getServer();
			String deliveryPath = PathUtility.buildPath(serverPath, model.getPath());

			ClientScheduleTaskRequestImpl request = new ClientScheduleTaskRequestImpl();
			request.setRequestUrl(deliveryPath);

			ApiScheduleTaskRequestImpl taskRequest = new ApiScheduleTaskRequestImpl();
			taskRequest.setTask(AppWebHookDeliveryTaskFactory.this.scheduleTaskName);
			taskRequest.setData(this.eventData);

			request.setTaskRequest(taskRequest);

			return request;
		}

	}

}
