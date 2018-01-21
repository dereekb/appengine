package com.dereekb.gae.web.taskqueue.model.extension.hook;

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

import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.impl.IterateTaskInputImpl;

/**
 * Task Queue controller used for hook events.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/taskqueue")
public class TaskQueueHookController {

	private static final Logger LOGGER = Logger.getLogger(TaskQueueIterateController.class.getName());

	private Map<String, TaskQueueHookControllerEntry> entries;

	public TaskQueueHookController(Map<String, TaskQueueHookControllerEntry> entries) {
		this.setEntries(entries);
	}

	public Map<String, TaskQueueHookControllerEntry> getEntries() {
		return this.entries;
	}

	public void setEntries(Map<String, TaskQueueHookControllerEntry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("entries cannot be null.");
		}

		this.entries = entries;
	}

	// MARK: Paths
	/**
	 * Handles a scheduled hook event.
	 *
	 * @param hookType
	 * @param parameters
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/hook/event/{type}", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void handleEvent(@PathVariable("type") String hookType,
	                        @RequestParam Map<String, String> parameters) {

		try {
			TaskQueueHookControllerEntry entry = this.getEntryForHookType(hookType);
			IterateTaskInputImpl input = new IterateTaskInputImpl(this.taskName, modelType, cursor, step,
			        this.parameters);
			IterateTaskRequestImpl request = new IterateTaskRequestImpl(input);
			entry.performIterateTask(request);
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "Hook task failed.", e);
			throw e;
		}
	}

}
