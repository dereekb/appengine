package com.dereekb.gae.client.api.server.schedule.impl;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.shared.builder.impl.AbstractSecuredClientModelRequestSender;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskRequest;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskResponse;
import com.dereekb.gae.client.api.server.schedule.ClientScheduleTaskServiceRequestSender;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;

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

	@Override
	public ClientScheduleTaskResponse scheduleTask(ClientScheduleTaskRequest request,
	                                               ClientRequestSecurity security)
	        throws ClientIllegalArgumentException,
	            ClientRequestFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientRequest buildClientRequest(ClientScheduleTaskRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientScheduleTaskResponse serializeResponseData(ClientScheduleTaskRequest request,
	                                                        ClientApiResponse response)
	        throws ClientResponseSerializationException {
		// TODO Auto-generated method stub
		return null;
	}

}
