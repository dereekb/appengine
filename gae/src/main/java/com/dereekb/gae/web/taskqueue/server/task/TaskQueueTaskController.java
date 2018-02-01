package com.dereekb.gae.web.taskqueue.server.task;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.utilities.collections.map.impl.CaseInsensitiveEntryContainerImpl;
import com.dereekb.gae.web.taskqueue.server.task.impl.TaskQueueTaskControllerRequestImpl;

/**
 * Arbitrary task controller.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/taskqueue")
public class TaskQueueTaskController extends CaseInsensitiveEntryContainerImpl<TaskQueueTaskControllerEntry> {

	private static final Logger LOGGER = Logger.getLogger(TaskQueueTaskController.class.getName());

	public TaskQueueTaskController() {
		super();
	}

	public TaskQueueTaskController(Map<String, TaskQueueTaskControllerEntry> entries) {
		super(entries);
	}

	/**
	 * Performs the requested task.
	 *
	 * @param task
	 *            Task name. Never {@code null}.
	 * @param parameters
	 *            Request parameters. Never {@code null}.
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/task/{task}", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void handleEvent(@PathVariable("task") String task,
	                        @RequestParam Map<String, String> parameters) {

		try {
			TaskQueueTaskControllerEntry entry = this.getEntryForType(task);
			TaskQueueTaskControllerRequestImpl request = new TaskQueueTaskControllerRequestImpl(task, parameters);
			entry.performTask(request);
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "Task failed.", e);
			throw e;
		}
	}

	public static String pathForTask(String taskName) {
		return String.format("/task/%s", taskName);
	}

	@Override
	public String toString() {
		return "TaskQueueTaskController [getEntries()=" + this.getEntries() + "]";
	}

}
