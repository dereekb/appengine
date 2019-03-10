package com.dereekb.gae.test.app.mock.client.crud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.impl.UpdateRequestImpl;
import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.UpdateRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.SimpleUpdateResponse;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;

/**
 * Test utility for {@link ClientUpdateRequestSender}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelClientUpdateRequestSenderTestUtility<T extends MutableUniqueModel> {

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientUpdateRequestSender<T> updateRequestSender;

	public ModelClientUpdateRequestSenderTestUtility(ClientUpdateRequestSender<T> updateRequestSender,
	        TestModelGenerator<T> testModelGenerator) {
		this.updateRequestSender = updateRequestSender;
		this.testModelGenerator = testModelGenerator;
	}

	public void testMockUpdateSingleRequest(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		T model = this.testModelGenerator.generate();
		T template = this.testModelGenerator.generate();
		template.setModelKey(model.getModelKey());

		UpdateRequest<T> updateRequest = new UpdateRequestImpl<T>(template);
		SerializedClientApiResponse<SimpleUpdateResponse<T>> response = this.updateRequestSender
		        .sendRequest(updateRequest, security);

		SimpleUpdateResponse<T> updateResponse = response.getSerializedResponse();
		Collection<T> results = updateResponse.getModels();

		assertFalse(results.isEmpty());

		// Update again.
		SimpleUpdateResponse<T> simpleUpdateResponse = this.updateRequestSender.update(updateRequest, security);
		assertTrue(simpleUpdateResponse.getModels().contains(model));
	}

	public void testMockUpdateManyRequest(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		int count = EditModelController.DEFAULT_MAX_ELEMENTS;

		List<T> models = this.testModelGenerator.generate(count);

		UpdateRequest<T> updateRequest = new UpdateRequestImpl<T>(models);
		SerializedClientApiResponse<SimpleUpdateResponse<T>> response = this.updateRequestSender
		        .sendRequest(updateRequest, security);

		SimpleUpdateResponse<T> updateResponse = response.getSerializedResponse();
		Collection<T> results = updateResponse.getModels();

		assertFalse(results.isEmpty());

		// Update again.
		SimpleUpdateResponse<T> simpleUpdateResponse = this.updateRequestSender.update(updateRequest, security);
		assertTrue(simpleUpdateResponse.getModels().containsAll(models));
	}

	public void testMockAtomicUnavailableUpdateRequest(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		T template = this.testModelGenerator.generate();
		template.setModelKey(this.testModelGenerator.generateKey());

		UpdateRequestOptions options = new UpdateRequestOptionsImpl(true);
		UpdateRequest<T> updateRequest = new UpdateRequestImpl<T>(template, options);
		SerializedClientApiResponse<SimpleUpdateResponse<T>> response = this.updateRequestSender
		        .sendRequest(updateRequest, security);

		assertFalse(response.isSuccessful());
		assertTrue(response.getError().getErrorType() == ClientApiResponseErrorType.OTHER_BAD_RESPONSE_ERROR);

		try {
			response.getSerializedResponse();
			fail("Should not have succeeeded.");
		} catch (ClientRequestFailureException e) {
			// Pass
		}

		// Try again. Should get ClientAtomicOperationException
		try {
			this.updateRequestSender.update(updateRequest, security);
			fail();
		} catch (ClientAtomicOperationException e) {
			List<ModelKey> missingKeys = e.getMissingKeys();
			assertTrue(missingKeys.contains(template.getModelKey()));
		} catch (Exception e) {
			fail();
			throw e;
		}
	}

	public void testMockNonAtomicUnavailableUpdateRequest(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		T model = this.testModelGenerator.generate();
		T template = this.testModelGenerator.generate();
		template.setModelKey(model.getModelKey());

		T unavailableTemplate = this.testModelGenerator.generate();
		unavailableTemplate.setModelKey(this.testModelGenerator.generateKey());

		List<T> templates = new ArrayList<T>();
		templates.add(template);
		templates.add(unavailableTemplate);

		boolean isAtomic = false;
		UpdateRequestOptions options = new UpdateRequestOptionsImpl(isAtomic);
		UpdateRequest<T> updateRequest = new UpdateRequestImpl<T>(templates, options);
		SerializedClientApiResponse<SimpleUpdateResponse<T>> response = this.updateRequestSender
		        .sendRequest(updateRequest, security);

		assertTrue(response.isSuccessful());

		// Get Serialized Primary Data
		try {
			SimpleUpdateResponse<T> simpleUpdateResponse = response.getSerializedResponse();

			Collection<T> successfulModels = simpleUpdateResponse.getModels();
			assertFalse(successfulModels.isEmpty());
			assertTrue(successfulModels.contains(template));

			Collection<ModelKey> missingKeys = simpleUpdateResponse.getMissingKeys();
			assertTrue(missingKeys.contains(unavailableTemplate.getModelKey()));
		} catch (ClientResponseSerializationException e) {
			fail("Should parse fine.");
		}

		// ClientUpdateService
		try {
			SimpleUpdateResponse<T> simpleUpdateResponse = this.updateRequestSender.update(updateRequest, security);

			Collection<T> successfulModels = simpleUpdateResponse.getModels();
			assertFalse(successfulModels.isEmpty());
			assertTrue(successfulModels.contains(template));

			Collection<ModelKey> missingKeys = simpleUpdateResponse.getMissingKeys();
			assertTrue(missingKeys.contains(unavailableTemplate.getModelKey()));
		} catch (ClientAtomicOperationException e) {
			fail("Is not an atomic operation.");
		} catch (Exception e) {
			fail();
			throw e;
		}

	}

	public void testMockUpdateWithoutIdentifier(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		T model = this.testModelGenerator.generate();
		T template = this.testModelGenerator.generate();
		template.setModelKey(null);

		UpdateRequest<T> updateRequest = new UpdateRequestImpl<T>(template);

		// Update again.
		try {
			SimpleUpdateResponse<T> simpleUpdateResponse = this.updateRequestSender.update(updateRequest, security);
			assertTrue(simpleUpdateResponse.getModels().contains(model));
		} catch (Exception e) {

		}
	}

	public void testEmptyMockUpdate(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		List<T> collection = Collections.emptyList();
		UpdateRequest<T> updateRequest = new UpdateRequestImpl<T>(collection);

		// Update again.
		try {
			this.updateRequestSender.update(updateRequest, security);
			fail();
		} catch (Exception e) {

		}
	}

	// TODO: Test for attribute failures.

}
