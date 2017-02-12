package com.dereekb.gae.test.applications.api.client;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.impl.ClientResponseImpl;
import com.dereekb.gae.client.api.service.sender.ClientRequestSender;
import com.dereekb.gae.test.spring.WebServiceTester;
import com.dereekb.gae.test.utility.mock.MockHttpServletRequestBuilderUtility;

/**
 * {@link ClientRequestSender} for unit tests.
 * 
 * @author dereekb
 *
 */
public class MockClientRequestSender
        implements ClientRequestSender {

	private WebServiceTester webServiceTester;

	public MockClientRequestSender() {};

	public WebServiceTester getWebServiceTester() {
		return this.webServiceTester;
	}

	public void setWebServiceTester(WebServiceTester webServiceTester) {
		if (webServiceTester == null) {
			throw new IllegalArgumentException("WebServiceTester cannot be null.");
		}

		this.webServiceTester = webServiceTester;
	}

	// MARK: ClientRequestSender
	@Override
	public ClientResponse sendRequest(ClientRequest request)
	        throws ClientConnectionException,
	            ClientRequestFailureException {

		MockHttpServletRequestBuilder builder = MockHttpServletRequestBuilderUtility.convert(request);
		ClientResponse response;

		try {
			MvcResult result = this.webServiceTester.mockMvcPerform(builder).andReturn();
			MockHttpServletResponse servletResponse = result.getResponse();

			int status = servletResponse.getStatus();
			String content = servletResponse.getContentAsString();
			response = new ClientResponseImpl(status, content);
		} catch (Exception e) {
			throw new ClientRequestFailureException(e);
		}

		return response;
	}

}
