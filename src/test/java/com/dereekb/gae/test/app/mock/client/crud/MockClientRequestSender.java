package com.dereekb.gae.test.app.mock.client.crud;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.impl.ClientResponseImpl;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.test.app.mock.web.WebServiceTester;
import com.dereekb.gae.test.app.mock.web.builder.WebServiceRequestBuilder;
import com.dereekb.gae.test.app.mock.web.impl.AbstractMockWebServiceTestUtility;
import com.dereekb.gae.test.utility.mock.MockHttpServletRequestBuilderUtility;

/**
 * {@link ClientRequestSender} for unit tests.
 * 
 * @author dereekb
 *
 */
public class MockClientRequestSender extends AbstractMockWebServiceTestUtility
        implements ClientRequestSender {

	// True by default to simplify taskqueue tests.
	private boolean waitForTaskQueue = false;

	public boolean isWaitForTaskQueue() {
		return this.waitForTaskQueue;
	}

	public void setWaitForTaskQueue(boolean waitForTaskQueue) {
		this.waitForTaskQueue = waitForTaskQueue;
	}

	// MARK: ClientRequestSender
	@Override
	public ClientResponse sendRequest(ClientRequest request)
	        throws ClientConnectionException,
	            ClientRequestFailureException {

		WebServiceTester webServiceTester = this.getWebServiceTester();
		WebServiceRequestBuilder webServiceRequestBuilder = webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder builder = MockHttpServletRequestBuilderUtility.convert(request,
		        webServiceRequestBuilder);

		ClientResponse response;

		try {
			MvcResult result = this.getWebServiceTester().mockMvcPerform(builder).andReturn();
			MockHttpServletResponse servletResponse = result.getResponse();

			int status = servletResponse.getStatus();
			String content = servletResponse.getContentAsString();
			response = new ClientResponseImpl(status, content);

			if (this.waitForTaskQueue) {
				webServiceTester.waitForTaskQueueToComplete();
			}
		} catch (Exception e) {
			throw new ClientRequestFailureException(e);
		}

		return response;
	}

}
