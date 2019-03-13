package com.dereekb.gae.server.app.model.hook.taskqueue.webhook;

import java.io.IOException;
import java.util.List;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskRequest;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskService;
import com.dereekb.gae.client.api.server.schedule.impl.ClientApiScheduleTaskRequestImpl;
import com.dereekb.gae.client.api.server.schedule.impl.ClientScheduleTaskRequestImpl;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedGetter;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyTransactionUtility;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.misc.path.PathUtility;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.api.server.hook.WebHookApiScheduleTaskControllerEntry;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateTaskFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Work;

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
	private GetterSetter<AppHook> appHookGetterSetter;
	private ClientScheduleTaskService scheduleService;

	private String scheduleTaskName = WebHookApiScheduleTaskControllerEntry.SCHEDULE_TASK_ENTRY_KEY;

	public AppWebHookDeliveryTaskFactory(ObjectifyKeyedGetter<App> appGetter,
	        GetterSetter<AppHook> appHookGetterSetter,
	        ClientScheduleTaskService scheduleService) {
		super();
		this.setAppGetter(appGetter);
		this.setAppHookGetterSetter(appHookGetterSetter);
		this.setScheduleService(scheduleService);
	}

	public ObjectifyKeyedGetter<App> getAppGetter() {
		return this.appGetter;
	}

	public void setAppGetter(ObjectifyKeyedGetter<App> appGetter) {
		if (appGetter == null) {
			throw new IllegalArgumentException("appGetter cannot be null.");
		}

		this.appGetter = appGetter;
	}

	public GetterSetter<AppHook> getAppHookGetterSetter() {
		return this.appHookGetterSetter;
	}

	public void setAppHookGetterSetter(GetterSetter<AppHook> appHookGetterSetter) {
		if (appHookGetterSetter == null) {
			throw new IllegalArgumentException("appHookGetterSetter cannot be null.");
		}

		this.appHookGetterSetter = appHookGetterSetter;
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

		@SuppressWarnings("unused")
		private final IterateTaskInput taskInput;
		private final ApiScheduleTaskRequest apiScheduleTaskRequest;

		public ScheduledAppTaskInstance(IterateTaskInput taskInput) throws IOException {
			this.taskInput = taskInput;

			// Setup ApiScheduleTaskRequest
			ClientApiScheduleTaskRequestImpl scheduleTaskRequest = new ClientApiScheduleTaskRequestImpl(
			        AppWebHookDeliveryTaskFactory.this.scheduleTaskName);

			String eventDataString = taskInput.getParameters().get(EVENT_DATA_KEY);

			// TODO: Can set tries and other arbitrary data to pass.

			scheduleTaskRequest.setRawData(eventDataString);
			this.apiScheduleTaskRequest = scheduleTaskRequest;
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
				case ROOT:
					// Send current system's credentials.
					security = ClientRequestSecurityImpl.systemSecurity();
					break;
			}

			try {
				AppWebHookDeliveryTaskFactory.this.scheduleService.scheduleTask(request, security);
			} catch (ClientRequestFailureException e) {
				this.handleDeliveryFailure(e, model);
			}
		}

		private ClientScheduleTaskRequest buildDeliveryRequest(App app,
		                                                       AppHook model) {

			String serverPath = app.getServer();
			String deliveryPath = PathUtility.buildPath(serverPath, model.getPath());

			ClientScheduleTaskRequestImpl request = new ClientScheduleTaskRequestImpl();
			request.setRequestUrl(deliveryPath);
			request.setTaskRequest(this.apiScheduleTaskRequest);

			return request;
		}

		private void handleDeliveryFailure(ClientRequestFailureException e,
		                                   AppHook model) {

			FailedTaskException exception = ObjectifyTransactionUtility.newTransact()
			        .doTransaction(new DeliveryFailureTransaction(e, model));

			if (exception != null) {
				throw exception;
			}
		}

		private class DeliveryFailureTransaction
		        implements Work<FailedTaskException> {

			private final ModelKey appHookKey;
			private final ClientRequestFailureException e;

			public DeliveryFailureTransaction(ClientRequestFailureException e, AppHook model) {
				this.appHookKey = model.getModelKey();
				this.e = e;
			}

			// MARK: Work
			@Override
			public FailedTaskException run() {
				AppHook hook = AppWebHookDeliveryTaskFactory.this.appHookGetterSetter.get(this.appHookKey);

				if (hook == null) {
					// Hook no longer exists. Don't send message.
					return null;
				}

				// Only increase failures here.
				Integer failures = hook.getFailures();
				hook.setFailures(failures += 1);

				AppWebHookDeliveryTaskFactory.this.appHookGetterSetter.update(hook);

				// TODO: Consider adding another field to AppHook that shows the
				// failures boundary, and discard once it is met.

				return new FailedTaskException(this.e);
			}

		}

	}

}
