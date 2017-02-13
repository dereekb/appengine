package com.dereekb.gae.test.applications.api.client;

import java.util.Collection;

import org.junit.Assert;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.impl.CreateRequestImpl;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.SimpleCreateResponse;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

/**
 * Test utility for {@link ClientCreateRequestSender}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelClientCreateRequestSenderTestUtility<T extends MutableUniqueModel> {

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientCreateRequestSender<T> createRequestSender;

	public ModelClientCreateRequestSenderTestUtility(ClientCreateRequestSender<T> createRequestSender,
	        TestModelGenerator<T> testModelGenerator) {
		this.createRequestSender = createRequestSender;
		this.testModelGenerator = testModelGenerator;
	}

	/**
	 * Tests trying to create a model on a type that does not support creating
	 * models.
	 */
	public void testCreateIsUnavailableRequest(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		T template = this.testModelGenerator.generate();
		template.setModelKey(null);

		CreateRequest<T> createRequest = new CreateRequestImpl<T>(template);

		try {
			SerializedClientApiResponse<CreateResponse<T>> response = this.createRequestSender
			        .sendRequest(createRequest, security);
			int status = response.getStatus();
			Assert.assertTrue(status == 405);
		} catch (NotClientApiResponseException e) {
			Assert.fail("Should be wrapped within a client API response.");
		}

		try {
			this.createRequestSender.create(createRequest);
			Assert.fail();
		} catch (ClientRequestFailureException e) {

		}

	}

	public void testMockCreateRequest(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		T template = this.testModelGenerator.generate();
		template.setModelKey(null);

		CreateRequest<T> createRequest = new CreateRequestImpl<T>(template);
		SerializedClientApiResponse<CreateResponse<T>> response = this.createRequestSender.sendRequest(createRequest,
		        security);

		SimpleCreateResponse<T> createResponse = response.getSerializedPrimaryData();
		Collection<T> models = createResponse.getCreatedModels();

		Assert.assertFalse(models.isEmpty());

		// Create again.
		CreateResponse<T> simpleCreateResponse = this.createRequestSender.create(createRequest);
		Assert.assertFalse(simpleCreateResponse.getCreatedModels().isEmpty());
	}

}
