package com.dereekb.gae.web.taskqueue.server.webhook;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.impl.JsonWebHookEventImpl;
import com.dereekb.gae.server.event.webhook.listener.WebHookEventSubmitter;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * TaskQueue controller for handling {@link WebHookEvent} related requests.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/taskqueue/webhook")
public class TaskQueueWebHookController {

	private static final String EVENT_PATH = "/event";
	private static final String SUBMIT_RETRY_PATH = "/submit/retry";

	public static final String WEBHOOK_EVENT_PATH = "/webhook" + EVENT_PATH;
	public static final String WEBHOOK_SUBMIT_RETRY_PATH = "/webhook" + SUBMIT_RETRY_PATH;

	private static final Logger LOGGER = Logger.getLogger(TaskQueueWebHookController.class.getName());

	private TaskQueueWebHookControllerDelegate delegate;

	public TaskQueueWebHookController(TaskQueueWebHookControllerDelegate delegate) {
		this.setDelegate(delegate);
	}

	public TaskQueueWebHookControllerDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(TaskQueueWebHookControllerDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: Controller
	/**
	 * Processes a scheduled event.
	 *
	 * @param task
	 *            Task name. Never {@code null}.
	 * @param parameters
	 *            Request parameters. Never {@code null}.
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = EVENT_PATH, method = RequestMethod.POST, consumes = "application/octet-stream")
	public void processEvent(@Valid @RequestBody JsonNode jsonEventData) {

		WebHookEvent webHookEvent = this.deserializeEvent(jsonEventData);

		try {
			this.delegate.processEvent(webHookEvent);
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "WebHook Event Process Failed.", e);
			throw e;
		}
	}

	/**
	 * Retries submitting the input {@link WebHookEvent} to the remote
	 * notification service.
	 *
	 * @see {@link WebHookEventSubmitter}.
	 *
	 * @param jsonEventData
	 *            Raw {@link JsonNode} request body.
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = SUBMIT_RETRY_PATH, method = RequestMethod.POST, consumes = "application/octet-stream")
	public void retrySendEvent(@Valid @RequestBody JsonNode jsonEventData) {
		WebHookEvent webHookEvent = this.deserializeEvent(jsonEventData);

		try {
			this.delegate.resubmitEvent(webHookEvent);
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "WebHook Submit Retry Failed.", e);
			throw e;
		}
	}

	// MARK: Internal
	private WebHookEvent deserializeEvent(@Valid JsonNode jsonEventData) throws ApiIllegalArgumentException {
		try {
			return new JsonWebHookEventImpl(jsonEventData);
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, "WebHook Deserialization Failed.", e);
			throw new ApiIllegalArgumentException(e);
		}
	}

	@Override
	public String toString() {
		return "TaskQueueWebHookController [delegate=" + this.delegate + "]";
	}

}
