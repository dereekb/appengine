package com.dereekb.gae.web.taskqueue.controller.extension.iterate;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.web.taskqueue.controller.extension.iterate.exception.UnregisteredIterateTypeException;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.impl.IterateTaskInputImpl;
import com.google.appengine.api.datastore.Cursor;

/**
 * Task Queue controller used for performing custom tasks over input models.
 *
 * @author dereekb
 *
 */
public final class TaskQueueIterateController {

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

	private Map<String, TaskQueueIterateControllerEntry> entries;

	public TaskQueueIterateController() {}

	public TaskQueueIterateController(Map<String, TaskQueueIterateControllerEntry> entries) {
		this.entries = entries;
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
	@RequestMapping(value = "{type}/iterate/{task}", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void iterate(@PathVariable("type") String modelType,
	                    @PathVariable("task") String taskName,
	                    @RequestHeader(value = TASK_STEP_HEADER, required = false) Integer step,
	                    @RequestHeader(value = CURSOR_HEADER, required = false) String cursorKey,
	                    @RequestParam Map<String, String> parameters) {

		TaskQueueIterateControllerEntry entry = this.getEntryForType(modelType);
		Cursor cursor = null;

		if (cursorKey != null) {
			cursor = Cursor.fromWebSafeString(cursorKey);
		}

		IterateTaskInputImpl input = new IterateTaskInputImpl(taskName, modelType, step, cursor, parameters);
		entry.performTask(input);
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

}
