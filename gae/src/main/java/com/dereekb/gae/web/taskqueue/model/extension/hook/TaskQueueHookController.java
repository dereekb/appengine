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

import com.dereekb.gae.utilities.collections.map.impl.CaseInsensitiveEntryContainerImpl;

/**
 * Task Queue controller used for web hooks.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/taskqueue")
public class TaskQueueHookController extends CaseInsensitiveEntryContainerImpl<TaskQueueHookControllerEntry> {

	private static final Logger LOGGER = Logger.getLogger(TaskQueueHookController.class.getName());

	public static final String HOOK_EVENT_TYPE_PARAMETER = "eventType";

	public TaskQueueHookController(Map<String, TaskQueueHookControllerEntry> entries) {
		super(entries);
	}

	// MARK: Paths
	/**
	 * Handles a scheduled hook event.
	 *
	 * @param hookType
	 * @param parameters
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/hook/event/{group}", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void handleEvent(@PathVariable("group") String hookEventGroup,
	                        @RequestParam(value = HOOK_EVENT_TYPE_PARAMETER, required = false) String hookEventType,
	                        @RequestParam Map<String, String> parameters) {

		try {
			TaskQueueHookControllerEntry entry = this.getEntryForType(hookEventGroup);

			// TODO: Complete hook events.

			// TaskQueueHookEvent event = new TaskQueueHookEventImpl(hookEventGroup, hookEventType, parameters);
			// IterateTaskRequestImpl request = new
			// IterateTaskRequestImpl(input);
			// entry.performIterateTask(request);
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "Hook task failed.", e);
			throw e;
		}
	}

}
