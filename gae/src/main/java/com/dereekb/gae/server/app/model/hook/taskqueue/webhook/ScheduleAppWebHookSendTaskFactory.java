package com.dereekb.gae.server.app.model.hook.taskqueue.webhook;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.app.model.hook.link.AppHookLinkSystemBuilderEntry;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateTaskFactory;

/**
 * {@link TaskQueueIterateTaskFactory} implementation for {@link AppHook} that
 * creates a new send task for each AppHook with send configuration's for that
 * model.
 *
 * @author dereekb
 *
 * @see AppHookEventListener
 * @see AppWebHookSendTaskFactory
 */
public class ScheduleAppWebHookSendTaskFactory
        implements TaskQueueIterateTaskFactory<AppHook> {

	public static final String TASK_KEY = "schedulehookevent";
	public static final String EVENT_DATA_KEY = "eventdata";

	private TaskScheduler scheduler;

	public TaskScheduler getScheduler() {
		return this.scheduler;
	}

	public void setScheduler(TaskScheduler scheduler) {
		if (scheduler == null) {
			throw new IllegalArgumentException("scheduler cannot be null.");
		}

		this.scheduler = scheduler;
	}

	// MARK: TaskQueueIterateTaskFactory
	@Override
	public Task<ModelKeyListAccessor<AppHook>> makeTask(IterateTaskInput input) throws FactoryMakeFailureException {
		return new ScheduledAppTaskInstance(input);
	}

	private class ScheduledAppTaskInstance
	        implements Task<ModelKeyListAccessor<AppHook>> {

		private final IterateTaskInput taskInput;

		public ScheduledAppTaskInstance(IterateTaskInput taskInput) {
			this.taskInput = taskInput;
		}

		// MARK: Task
		@Override
		public void doTask(ModelKeyListAccessor<AppHook> input) throws FailedTaskException {
			List<TaskRequest> requests = new ArrayList<TaskRequest>();

			for (ModelKey key : input.getModelKeys()) {
				TaskRequest request = this.makeRequest(key, input);
				requests.add(request);
			}

			ScheduleAppWebHookSendTaskFactory.this.scheduler.schedule(requests);
		}

		private TaskRequest makeRequest(ModelKey key,
		                                ModelKeyListAccessor<AppHook> input) {

			CaseInsensitiveMap<String> parameters = this.taskInput.getParameters();
			String eventData = parameters.get(EVENT_DATA_KEY);

			// Create Iterate Sequence Path
			String taskPath = TaskQueueIterateController.pathForSequenceTask(
			        AppHookLinkSystemBuilderEntry.LINK_MODEL_TYPE, AppWebHookSendTaskFactory.TASK_KEY);

			TaskRequestImpl taskRequest = new TaskRequestImpl(taskPath);

			KeyedEncodedParameter keysParameter = new KeyedEncodedParameterImpl(
			        TaskQueueIterateController.SEQUENCE_KEYS_PARAM_NAME, key.toString());
			taskRequest.replaceParameter(keysParameter);

			KeyedEncodedParameter eventDataParameter = new KeyedEncodedParameterImpl(
			        AppWebHookSendTaskFactory.EVENT_DATA_KEY, eventData);
			taskRequest.replaceParameter(eventDataParameter);

			return taskRequest;
		}

		@Override
		public String toString() {
			return "ScheduledAppTaskInstance [taskInput=" + this.taskInput + "]";
		}

	}

	@Override
	public String toString() {
		return "ScheduleAppWebHookSendTaskFactory [scheduler=" + this.scheduler + "]";
	}

}
