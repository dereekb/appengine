package com.dereekb.gae.web.api.taskqueue.controller.extension.iterate;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.model.extension.taskqueue.iterate.impl.IterateTaskInputImpl;
import com.dereekb.gae.web.api.taskqueue.controller.extension.iterate.exception.UnregisteredIterateTypeException;
import com.google.appengine.api.datastore.Cursor;

/**
 * Task Queue controller used for performing custom tasks over input models.
 *
 * @author dereekb
 *
 */
public final class TaskQueueIterateController {

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

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "{type}/iterate", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void iterate(@PathVariable("type") String modelType,
	                   @RequestHeader(value=IterateTaskInputImpl.DEFAULT_TASK_STEP_HEADER, required = false) Integer step,
	                   @RequestHeader(value="cursor", required=false) String cursorKey,
	                   @RequestParam(value = "task", required = true) String taskName,
	                   @RequestParam Map<String, String> parameters) {

		TaskQueueIterateControllerEntry entry = this.getEntryForType(modelType);
		Cursor cursor = null;

		if (cursorKey != null) {
			cursor = Cursor.fromWebSafeString(cursorKey);
		}

		IterateTaskInputImpl input = new IterateTaskInputImpl(step, cursor, parameters);
		entry.performTask(taskName, input);
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
