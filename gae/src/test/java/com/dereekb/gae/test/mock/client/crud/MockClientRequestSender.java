package com.dereekb.gae.test.mock.client.crud;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.impl.ClientResponseImpl;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.test.mock.AbstractMockWebServiceTestUtility;
import com.dereekb.gae.test.utility.mock.MockHttpServletRequestBuilderUtility;

/**
 * {@link ClientRequestSender} for unit tests.
 * 
 * @author dereekb
 *
 */
public class MockClientRequestSender extends AbstractMockWebServiceTestUtility
        implements ClientRequestSender {

	// MARK: ClientRequestSender
	@Override
	public ClientResponse sendRequest(ClientRequest request)
	        throws ClientConnectionException,
	            ClientRequestFailureException {

		MockHttpServletRequestBuilder builder = MockHttpServletRequestBuilderUtility.convert(request);
		ClientResponse response;

		try {
			MvcResult result = this.getWebServiceTester().mockMvcPerform(builder).andReturn();
			MockHttpServletResponse servletResponse = result.getResponse();

			int status = servletResponse.getStatus();
			String content = servletResponse.getContentAsString();
			response = new ClientResponseImpl(status, content);

			this.getWebServiceTester().waitForTaskQueueToComplete();
		} catch (Exception e) {
			throw new ClientRequestFailureException(e);
		}

		return response;
	}

}
