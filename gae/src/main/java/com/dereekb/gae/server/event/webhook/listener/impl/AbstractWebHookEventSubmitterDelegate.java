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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

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

	public AbstractWebHookEventSubmitterDelegate(TaskRequestSender<WebHookEvent> rescheduleTaskSender) {
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
		try {
			this.trySubmitEvent(webHookEvent);
		} catch (WebHookEventSubmitException e) {
			this.rescheduleEventSubmit(webHookEvent);
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

	protected static class RescheduleWebHookEventTaskRequestBuilder extends AbstractIndividualTaskRequestBuilder<WebHookEvent> {

		private static TaskRequest DEFAULT_TASK_REQUEST = new TaskRequestImpl("", Method.POST);

		public RescheduleWebHookEventTaskRequestBuilder() throws IllegalArgumentException {
			super(DEFAULT_TASK_REQUEST);
		}

		@Override
		protected Collection<? extends KeyedEncodedParameter> buildRequestParameters(WebHookEvent entity) {
			return null;
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
