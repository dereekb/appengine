package com.dereekb.gae.server.app.model.hook.taskqueue.webhook;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateTaskFactory;

/**
 * {@link TaskQueueIterateTaskFactory} implementation for {@link AppHook} that
 * finally sends the events.
 * <p>
 *
 *
 * @author dereekb
 *
 * @see ScheduleAppWebHookSendTaskFactory
 */
public class AppWebHookSendTaskFactory
        implements TaskQueueIterateTaskFactory<AppHook> {

	public static final String TASK_KEY = "sendhookevent";
	public static final String EVENT_DATA_KEY = "eventdata";

	// TODO: Complete

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
			for (ModelKey key : input.getModelKeys()) {

			}
		}

	}

}
