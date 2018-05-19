package com.dereekb.gae.client.api.server.schedule.impl;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.shared.builder.impl.AbstractSecuredClientModelRequestSender;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskRequest;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskResponse;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskServiceRequestSender;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestDataImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestUrlImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientScheduleTaskServiceRequestSender} implementation.
 *
 * @author dereekb
 *
 */
public class ClientScheduleTaskServiceRequestSenderImpl extends AbstractSecuredClientModelRequestSender<ClientScheduleTaskRequest, ClientScheduleTaskResponse>
        implements ClientScheduleTaskServiceRequestSender {

	public ClientScheduleTaskServiceRequestSenderImpl(SecuredClientApiRequestSender requestSender)
	        throws IllegalArgumentException {
		super(requestSender);
	}

	// MARK: ClientScheduleTaskServiceRequestSender
	@Override
	public ClientScheduleTaskResponse scheduleTask(ClientScheduleTaskRequest request,
	                                               ClientRequestSecurity security)
	        throws ClientIllegalArgumentException,
	            ClientRequestFailureException {
		return this.sendRequest(request, security).getSerializedResponse();
	}

	@Override
	public ClientRequest buildClientRequest(ClientScheduleTaskRequest request) {

		if (request.getTaskRequest().getTask() == null) {
			throw new IllegalArgumentException("Task name should not be null.");
		}

		ClientRequestUrl url = new ClientRequestUrlImpl(request.getRequestUrl());
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.POST);

		ObjectMapper mapper = this.getObjectMapper();
		ApiScheduleTaskRequest taskRequestData = request.getTaskRequest();

		ClientRequestDataImpl requestData = ClientRequestDataImpl.make(mapper, taskRequestData);
		clientRequest.setData(requestData);

		return clientRequest;
	}

	@Override
	public ClientScheduleTaskResponse serializeResponseData(ClientScheduleTaskRequest request,
	                                                        ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientScheduleTaskResponseImpl(request, response);
	}

	protected class ClientScheduleTaskResponseImpl extends AbstractSerializedResponse
	        implements ClientScheduleTaskResponse {

		private final ClientScheduleTaskRequest request;

		public ClientScheduleTaskResponseImpl(ClientScheduleTaskRequest request, ClientApiResponse response) {
			super(response);
			this.request = request;
		}

		public ClientScheduleTaskRequest getRequest() {
			return this.request;
		}

	}

}
