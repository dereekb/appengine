package com.dereekb.gae.web.taskqueue.controller.extension.iterate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.exception.UnregisteredIterateTypeException;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.impl.IterateTaskInputImpl;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Task Queue controller used for performing custom tasks over input models.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/taskqueue")
public class TaskQueueIterateController {

	/**
	 * HTTP header for the step value.
	 *
	 * @see {@link #iterate(String, Integer, String, String, Map)}
	 */
	public static final String TASK_STEP_HEADER = "TQ_ITERATE_STEP";

	/**
	 * HTTP header for the cursor value.
	 *
	 * @see {@link #iterate(String, Integer, String, String, Map)}
	 */
	public static final String CURSOR_HEADER = "TQ_CURSOR";

	private TaskScheduler scheduler;
	private Map<String, TaskQueueIterateControllerEntry> entries;

	public TaskQueueIterateController() {}

	public TaskQueueIterateController(TaskScheduler scheduler, Map<String, TaskQueueIterateControllerEntry> entries) {
		this.setScheduler(scheduler);
		this.setEntries(entries);
	}

	public TaskScheduler getScheduler() {
		return this.scheduler;
	}

	public void setScheduler(TaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Map<String, TaskQueueIterateControllerEntry> getEntries() {
		return this.entries;
	}

	public void setEntries(Map<String, TaskQueueIterateControllerEntry> entries) {
		this.entries = entries;
	}

	/**
	 * Performs an iteration task using a
	 * {@link TaskQueueIterateControllerEntry} that corresponds to the input
	 * {@code modelType} value. The {@code step} and {@code cursorKey} values
	 * are for the Task Queue.
	 *
	 * @param modelType
	 *            The model type. Never {@code null}.
	 * @param step
	 *            The current iteration step, or {@code null} if it is the first
	 *            step.
	 * @param cursorKey
	 *            The cursor key, or {@code null} if the request has not yet
	 *            been started.
	 * @param taskName
	 *            The task name to use. Never {@code null}.
	 * @param parameters
	 *            All request parameters. Never {@code null}.
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "{type}/iterate/{task}", method = RequestMethod.POST, consumes = "application/octet-stream")
	public void iterate(@PathVariable("type") String modelType,
	                    @PathVariable("task") String taskName,
	                    @RequestHeader(value = TASK_STEP_HEADER, required = false) Integer step,
	                    @RequestHeader(value = CURSOR_HEADER, required = false) String cursor,
	                    @RequestParam Map<String, String> parameters) {

		TaskQueueIterateControllerEntry entry = this.getEntryForType(modelType);
		IterateTaskInputImpl input = new IterateTaskInputImpl(taskName, modelType, cursor, step, parameters);
		IterateTaskRequestImpl request = new IterateTaskRequestImpl(input);
		entry.performTask(request);
	}

	private TaskQueueIterateControllerEntry getEntryForType(String modelType) throws UnregisteredIterateTypeException {
		TaskQueueIterateControllerEntry entry = this.entries.get(modelType);

		if (entry == null) {
			throw new UnregisteredIterateTypeException();
		}

		return entry;
	}

	@Override
	public String toString() {
		return "TaskQueueIterateController [entries=" + this.entries + "]";
	}

	private class IterateTaskRequestImpl
	        implements IterateTaskRequest {

		private final IterateTaskInput taskInput;

		public IterateTaskRequestImpl(IterateTaskInputImpl taskInput) {
			this.taskInput = taskInput;
		}

		@Override
		public IterateTaskInput getTaskInput() {
			return this.taskInput;
		}

		@Override
		public void scheduleContinuation(String cursor) {
			String path = this.getContinutationPath();
			Collection<KeyedEncodedParameter> headers = this.getContinuationHeaders(cursor);
			Collection<KeyedEncodedParameter> parameters = this.getContinuationParameters();

			TaskRequestImpl request = new TaskRequestImpl(path, Method.POST);
			request.setHeaders(headers);
			request.setParameters(parameters);

			TaskQueueIterateController.this.scheduler.schedule(request);
		}

		private String getContinutationPath() {
			String taskName = this.taskInput.getTaskName();
			String modelType = this.taskInput.getModelType();
			return String.format("%s/iterate/%s", modelType, taskName);
		}

		private Collection<KeyedEncodedParameter> getContinuationHeaders(String cursor) {
			Integer step = this.taskInput.getIterationStep() + 1;

			List<KeyedEncodedParameter> parameters = new ArrayList<KeyedEncodedParameter>();
			parameters.add(new KeyedEncodedParameterImpl(TASK_STEP_HEADER, step));
			parameters.add(new KeyedEncodedParameterImpl(CURSOR_HEADER, cursor));

			return parameters;
		}

		private Collection<KeyedEncodedParameter> getContinuationParameters() {
			Map<String, String> parameters = this.taskInput.getParameters();
			List<KeyedEncodedParameterImpl> impl = KeyedEncodedParameterImpl.makeParametersWithMap(parameters);
			return new ArrayList<KeyedEncodedParameter>(impl);
		}

	}

}
