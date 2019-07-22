package com.dereekb.gae.test.app.taskqueue.server.webhook;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskService;
import com.dereekb.gae.client.api.server.schedule.impl.ClientScheduleTaskServiceRequestSenderImpl;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.model.shared.event.impl.CommonModelEventType;
import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelKeyWebHookEventDataImpl;
import com.dereekb.gae.server.event.webhook.WebHookEvent;
import com.dereekb.gae.server.event.webhook.impl.WebHookEventImpl;
import com.dereekb.gae.server.event.webhook.listener.WebHookEventSubmitterDelegate;
import com.dereekb.gae.server.event.webhook.listener.exception.WebHookEventSubmitException;
import com.dereekb.gae.server.event.webhook.listener.impl.WebHookEventSubmitterDelegateImpl;
import com.dereekb.gae.server.event.webhook.listener.impl.WebHookEventSubmitterImpl;
import com.dereekb.gae.server.event.webhook.service.WebHookEventConverter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskScheduler;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.web.taskqueue.server.webhook.impl.TaskQueueWebHookControllerDelegateImpl;

/**
 *
 * @author dereekb
 *
 */
public class WebHookEventSubmitterDelegateImplTest extends AbstractAppTestingContext {

	@Autowired
	@Qualifier("taskScheduler")
	private TaskScheduler taskScheduler;

	@Autowired
	@Qualifier("taskQueueWebHookControllerDelegate")
	private TaskQueueWebHookControllerDelegateImpl webHookControllerDelegate;

	@Autowired
	@Qualifier("webHookEventConverter")
	private WebHookEventConverter webHookEventConverter;

	@Autowired
	@Qualifier("securedClientRequestSender")
	private SecuredClientApiRequestSender securedClientApiRequestSender;

	protected transient TestWebHookEventSubmitterDelegateImpl testEventSubmitterDelegate;
	protected transient TestWebHookEventSubmitterImpl testEventSubmitter;

	@BeforeEach
	public void initializeForWebHookTests() {
		ClientScheduleTaskServiceRequestSenderImpl localScheduleTaskSender = new ClientScheduleTaskServiceRequestSenderImpl(
		        this.securedClientApiRequestSender);
		this.testEventSubmitterDelegate = new TestWebHookEventSubmitterDelegateImpl(this.taskScheduler,
		        localScheduleTaskSender);
		this.testEventSubmitter = new TestWebHookEventSubmitterImpl(this.webHookEventConverter,
		        this.testEventSubmitterDelegate);
		this.webHookControllerDelegate.setEventSubmitter(this.testEventSubmitter);
	}

	@Test
	public void testReschedule() throws IOException {

		EventType eventType = CommonModelEventType.CREATED;
		WebHookEventImpl event = new WebHookEventImpl(eventType);

		ModelKeyWebHookEventDataImpl eventData = new ModelKeyWebHookEventDataImpl();
		eventData.setKeys(ListUtility.toList("F_10157213088039532"));
		eventData.setModelType("LoginPointer");

		event.setData(eventData);

		this.testEventSubmitterDelegate.testTrySubmitEvent(event);

		this.waitForTaskQueueToComplete();
	}


	// MARK: Internal
	protected class TestWebHookEventSubmitterImpl extends WebHookEventSubmitterImpl {

		public TestWebHookEventSubmitterImpl(WebHookEventConverter converter, WebHookEventSubmitterDelegate delegate) {
			super(converter, delegate);
		}

	}

	protected class TestWebHookEventSubmitterDelegateImpl extends WebHookEventSubmitterDelegateImpl {

		public TestWebHookEventSubmitterDelegateImpl(TaskScheduler scheduler,
		        ClientScheduleTaskService scheduleTaskService) {
			super(scheduler, scheduleTaskService);
		}

		public void testTrySubmitEvent(WebHookEvent event) throws WebHookEventSubmitException {
			this.trySubmitEvent(event);
		}

	}

}
