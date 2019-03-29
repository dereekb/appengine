package com.dereekb.gae.test.app.mock.client.crud;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
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
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;

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

	// MARK: Tests
	/**
	 * Tests trying to create a model on a type that does not support creating
	 * models.
	 */
	public void testCreateIsUnavailableRequest(ClientRequestSecurity security) throws ClientRequestFailureException {

		T template = this.testModelGenerator.generate();
		template.setModelKey(null);

		CreateRequest<T> createRequest = new CreateRequestImpl<T>(template);

		try {
			SerializedClientApiResponse<CreateResponse<T>> response = this.createRequestSender
			        .sendRequest(createRequest, security);
			int status = response.getStatus();
			assertTrue(status == 405, "Create request should have failed with status 405, but instead got: " + status + " - " + response.getResponseData());
		} catch (NotClientApiResponseException e) {
			fail("Should be wrapped within a client API response.");
		}

		try {
			this.createRequestSender.create(createRequest, security);
			fail();
		} catch (ClientRequestFailureException e) {

		}

	}

	public void testMockCreateRequest(ClientRequestSecurity security) throws ClientRequestFailureException {

		T template = this.testModelGenerator.generate();
		template.setModelKey(null);

		CreateRequest<T> createRequest = new CreateRequestImpl<T>(template);
		this.testMockCreateRequest(createRequest, security);
	}

	public void testMockCreateRequest(CreateRequest<T> createRequest, ClientRequestSecurity security) throws ClientRequestFailureException {

		SerializedClientApiResponse<CreateResponse<T>> response = this.createRequestSender.sendRequest(createRequest,
		        security);

		SimpleCreateResponse<T> createResponse = response.getSerializedResponse();
		Collection<T> models = createResponse.getModels();

		assertFalse(models.isEmpty());

		// Create again.
		CreateResponse<T> simpleCreateResponse = this.createRequestSender.create(createRequest, security);
		assertFalse(simpleCreateResponse.getModels().isEmpty());
	}

	public void testMockCreateNothingRequest(ClientRequestSecurity security) throws ClientRequestFailureException {

		List<T> noTemplates = new ArrayList<T>();
		CreateRequest<T> createRequest = new CreateRequestImpl<T>(noTemplates);

		try {
			this.createRequestSender.create(createRequest, security);
			fail("Should have failed request.");
		} catch (ClientRequestFailureException e) {
			//e.printStackTrace();
		}
	}

	public void testMockCreateTooManyRequest(ClientRequestSecurity security) throws ClientRequestFailureException {

		int moreThanMax = EditModelController.MAX_ATOMIC_EDIT_SIZE + 5;

		List<T> tooManyTemplates = this.testModelGenerator.generate(moreThanMax);
		CreateRequest<T> createRequest = new CreateRequestImpl<T>(tooManyTemplates);

		try {
			this.createRequestSender.create(createRequest, security);
			fail("Should have failed request.");
		} catch (ClientTooMuchInputException e) {

		}
	}

}
