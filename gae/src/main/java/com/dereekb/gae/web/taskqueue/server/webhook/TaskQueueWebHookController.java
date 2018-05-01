package com.dereekb.gae.web.taskqueue.server.webhook;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taskqueue/webhook")
public class TaskQueueWebHookController {

	/**
	 * Performs the requested task.
	 *
	 * @param task
	 *            Task name. Never {@code null}.
	 * @param parameters
	 *            Request parameters. Never {@code null}.
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/submit/retry", method = RequestMethod.POST, consumes = "application/octet-stream")
	public void retrySendEvent(@RequestParam Map<String, String> parameters) {

		// TODO: Retry sending event.

	}

}
