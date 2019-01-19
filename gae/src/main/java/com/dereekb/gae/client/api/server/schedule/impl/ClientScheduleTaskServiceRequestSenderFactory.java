package com.dereekb.gae.client.api.server.schedule.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskRequest;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskResponse;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskServiceRequestSender;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.impl.AbstractSerializedClientApiResponseImpl;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.utilities.gae.GoogleAppEngineContextualFactory;
import com.dereekb.gae.utilities.gae.impl.GoogleAppEngineContextualFactoryImpl;

/**
 * {@link GoogleAppEngineContextualFactory} for
 * {@link ClientScheduleTaskServiceRequestSender}.
 *
 * @author dereekb
 *
 */
public class ClientScheduleTaskServiceRequestSenderFactory extends GoogleAppEngineContextualFactoryImpl<ClientScheduleTaskServiceRequestSender> {

	public ClientScheduleTaskServiceRequestSenderFactory() {
		super(true);
		this.setTestSingleton(TestClientScheduleTaskServiceRequestSender.SINGLETON);
	}

	public static class TestClientScheduleTaskServiceRequestSender
	        implements ClientScheduleTaskServiceRequestSender {

		private static final Logger LOGGER = Logger
		        .getLogger(TestClientScheduleTaskServiceRequestSender.class.getName());

		public static final TestClientScheduleTaskServiceRequestSender SINGLETON = new TestClientScheduleTaskServiceRequestSender();

		private static final TestClientScheduleTaskResponseImpl RESPONSE = new TestClientScheduleTaskResponseImpl();

		@Override
		public ClientScheduleTaskResponse scheduleTask(ClientScheduleTaskRequest request,
		                                               ClientRequestSecurity security)
		        throws ClientIllegalArgumentException,
		            ClientRequestFailureException {
			return this.sendRequest(request, security).getSerializedResponse();
		}

		@Override
		public SerializedClientApiResponse<ClientScheduleTaskResponse> sendRequest(ClientScheduleTaskRequest request,
		                                                                           ClientRequestSecurity security)
		        throws NotClientApiResponseException,
		            ClientConnectionException,
		            ClientAuthenticationException,
		            ClientTooMuchInputException,
		            ClientRequestFailureException {

			if (request.getTaskRequest().getTask() == null) {
				throw new IllegalArgumentException("Task should not be null.");
			}

			LOGGER.log(Level.INFO,
			        "Test Scheduling Task Request: \n" + "TO: " + request.getRequestUrl() + "\n" + "TASK: "
			                + request.getTaskRequest().getTask() + "\n" + "HEADERS: "
			                + request.getTaskRequest().getEncodedHeaders() + "\n" + "PARAMETERS: "
			                + request.getTaskRequest().getEncodedParameters() + "\n" + "DATA: "
			                + request.getTaskRequest().getData().toString() + "\n" + "");

			return RESPONSE;
		}

		private static class TestClientScheduleTaskResponseImpl extends AbstractSerializedClientApiResponseImpl<ClientScheduleTaskResponse>
		        implements ClientScheduleTaskResponse {

			@Override
			public ClientScheduleTaskResponse getSerializedResponse()
			        throws ClientRequestFailureException,
			            ClientResponseSerializationException {
				return this;
			}

		}

	}

}
