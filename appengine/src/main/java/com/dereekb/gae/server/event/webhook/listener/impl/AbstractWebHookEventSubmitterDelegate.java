package com.dereekb.gae.server.event.webhook.listener.impl;

import java.util.Collection;

import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.listener.WebHookEventSubmitterDelegate;
import com.dereekb.gae.server.event.webhook.listener.exception.WebHookEventSubmitException;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskRequestImpl;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.AbstractIndividualTaskRequestBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.impl.TaskRequestSenderImpl;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.web.taskqueue.server.webhook.TaskQueueWebHookController;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * {@link WebHookEventSubmitterDelegate} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractWebHookEventSubmitterDelegate
        implements WebHookEventSubmitterDelegate {

	private TaskRequestSender<WebHookEvent> rescheduleTaskSender;

	public AbstractWebHookEventSubmitterDelegate(TaskScheduler scheduler) {
		this(makeDefaultTaskRequestSender(scheduler));
	}

	protected AbstractWebHookEventSubmitterDelegate(TaskRequestSender<WebHookEvent> rescheduleTaskSender) {
		this.setRescheduleTaskSender(rescheduleTaskSender);
	}

	public TaskRequestSender<WebHookEvent> getRescheduleTaskSender() {
		return this.rescheduleTaskSender;
	}

	public void setRescheduleTaskSender(TaskRequestSender<WebHookEvent> rescheduleTaskSender) {
		if (rescheduleTaskSender == null) {
			throw new IllegalArgumentException("rescheduleTaskSender cannot be null.");
		}

		this.rescheduleTaskSender = rescheduleTaskSender;
	}

	// MARK: WebHookEventSubmitterDelegate
	@Override
	public final void submitEvent(WebHookEvent webHookEvent) {
		this.submitEvent(webHookEvent, true);
	}

	@Override
	public final void submitEvent(WebHookEvent webHookEvent,
	                              boolean scheduleRetryOnFailure)
	        throws WebHookEventSubmitException {
		try {
			this.trySubmitEvent(webHookEvent);
		} catch (WebHookEventSubmitException e) {
			if (scheduleRetryOnFailure) {
				this.rescheduleEventSubmit(webHookEvent);
			} else {
				throw e;
			}
		}
	}

	protected abstract void trySubmitEvent(WebHookEvent event) throws WebHookEventSubmitException;

	protected void rescheduleEventSubmit(WebHookEvent event) {
		this.rescheduleTaskSender.sendTask(event);
	}

	protected static TaskRequestSender<WebHookEvent> makeDefaultTaskRequestSender(TaskScheduler scheduler) {
		TaskRequestBuilder<WebHookEvent> builder = new RescheduleWebHookEventTaskRequestBuilder();
		TaskRequestSenderImpl<WebHookEvent> sender = new TaskRequestSenderImpl<WebHookEvent>(scheduler, builder);
		return sender;
	}

	/**
	 * TaskRequestBuilder that targets the {@link TaskQueueWebHookController#retrySendEvent()} endpoint.
	 *
	 * @author dereekb
	 *
	 */
	protected static class RescheduleWebHookEventTaskRequestBuilder extends AbstractIndividualTaskRequestBuilder<WebHookEvent> {

		private static TaskRequest DEFAULT_TASK_REQUEST = new TaskRequestImpl(TaskQueueWebHookController.WEBHOOK_SUBMIT_RETRY_PATH);

		public RescheduleWebHookEventTaskRequestBuilder() throws IllegalArgumentException {
			super(DEFAULT_TASK_REQUEST);
		}

		@Override
		protected Collection<? extends KeyedEncodedParameter> buildRequestParameters(WebHookEvent entity) {
			return null;	// No parameters.
		}

		@Override
		protected TaskRequestImpl buildRequest(TaskRequestImpl request,
		                                       WebHookEvent entity) {
			String data;

			try {
				data = ObjectMapperUtilityBuilderImpl.MAPPER.writeValueAsString(entity.getJsonNode());
				request.setRequestData(data);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}

			return request;
		}

	}

	@Override
	public String toString() {
		return "AbstractWebHookEventSubmitterDelegate [rescheduleTaskSender=" + this.rescheduleTaskSender + "]";
	}

}
