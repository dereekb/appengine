package com.dereekb.gae.client.api.service.sender.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.impl.ClientResponseImpl;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.utilities.gae.GoogleAppEngineContextualFactory;
import com.dereekb.gae.utilities.gae.impl.GoogleAppEngineContextualFactoryImpl;

/**
 * {@link GoogleAppEngineContextualFactory} for {@link ClientRequestSender}.
 * <p>
 * Is only useful in cases where a no-data-response is useful in a generic
 * sense. Request senders should really use their own modified implementations
 * depending on the current state.
 *
 * @author dereekb
 *
 */
public class ClientRequestSenderFactory extends GoogleAppEngineContextualFactoryImpl<ClientRequestSender> {

	public ClientRequestSenderFactory() {
		super(true);
		this.setTestSingleton(TestClientRequestSender.SINGLETON);

		// TODO: Set development singleton that modifies the request and points
		// them to the shared local development.
	}

	/**
	 *
	 * @author dereekb
	 *
	 */
	public static class TestClientRequestSender
	        implements ClientRequestSender {

		private static final Logger LOGGER = Logger.getLogger(TestClientRequestSender.class.getName());

		public static final TestClientRequestSender SINGLETON = new TestClientRequestSender();

		@Override
		public ClientResponse sendRequest(ClientRequest request)
		        throws ClientConnectionException,
		            ClientRequestFailureException {

			LOGGER.log(Level.INFO,
			        "Sending client request: \n" + "TO: " + request.getUrl().getRelativeUrlPath().getPath() + "\n"
			                + "METHOD: " + request.getMethod().toString() + "\n" + "HEADERS: "
			                + request.getHeaders().toString() + "\n" + "PARAMETERS: "
			                + request.getParameters().toString() + "\n" + "DATA: " + request.getData().getDataString()
			                + "\n" + "");

			return new ClientResponseImpl(200, null);
		}

	}

}
