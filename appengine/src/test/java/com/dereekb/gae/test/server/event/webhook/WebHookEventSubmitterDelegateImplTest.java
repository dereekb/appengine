package com.dereekb.gae.test.server.event.webhook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskRequest;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskResponse;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskService;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.model.shared.event.impl.CommonModelEventType;
import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelKeyWebHookEventDataImpl;
import com.dereekb.gae.server.event.webhook.impl.WebHookEventImpl;
import com.dereekb.gae.server.event.webhook.listener.impl.WebHookEventSubmitterDelegateImpl;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.google.appengine.api.taskqueue.TaskAlreadyExistsException;

public class WebHookEventSubmitterDelegateImplTest {

	@Test
	public void testReschedulingEvent() {

		TaskScheduler testScheduler = new TaskScheduler() {

			@Override
			public void schedule(Collection<? extends TaskRequest> requests)
			        throws SubmitTaskException,
			            TaskAlreadyExistsException {

				for (TaskRequest request : requests) {
					this.schedule(request);
				}
			}

			@Override
			public void schedule(TaskRequest request) throws SubmitTaskException, TaskAlreadyExistsException {

				assertFalse(request.getPath().getPathComponents().isEmpty(), "The path should not be empty.");
				assertNotNull(request.getRequestData(), "The data should not be null.");

				// Do nothing.
			}

		};

		ClientScheduleTaskService testScheduleTaskService = new ClientScheduleTaskService() {

			@Override
			public ClientScheduleTaskResponse scheduleTask(ClientScheduleTaskRequest request,
			                                               ClientRequestSecurity security)
			        throws ClientIllegalArgumentException,
			            ClientRequestFailureException {
				throw new ClientRequestFailureException("Force rescheduling the task.");
			}

		};


		WebHookEventSubmitterDelegateImpl delegate = new WebHookEventSubmitterDelegateImpl(testScheduler, testScheduleTaskService);

		EventType eventType = CommonModelEventType.CREATED;
		WebHookEventImpl event = new WebHookEventImpl(eventType);

		ModelKeyWebHookEventDataImpl eventData = new ModelKeyWebHookEventDataImpl();
		eventData.setKeys(ListUtility.toList("F_10157213088039532"));
		eventData.setModelType("LoginPointer");

		event.setData(eventData);

		delegate.submitEvent(event);

	}

}
