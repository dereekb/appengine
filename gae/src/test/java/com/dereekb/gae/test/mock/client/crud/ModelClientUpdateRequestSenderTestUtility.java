package com.dereekb.gae.test.mock.client.crud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;

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

	public void testMockUpdateRequest(ClientRequestSecurity security)
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

		SimpleUpdateResponse<T> updateResponse = response.getSerializedPrimaryData();
		Collection<T> models = updateResponse.getModels();

		Assert.assertFalse(models.isEmpty());

		// Update again.
		SimpleUpdateResponse<T> simpleUpdateResponse = this.updateRequestSender.update(updateRequest);
		Assert.assertTrue(simpleUpdateResponse.getModels().contains(model));
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

		Assert.assertFalse(response.getSuccess());
		Assert.assertTrue(response.getError().getErrorType() == ClientApiResponseErrorType.OTHER_BAD_RESPONSE_ERROR);

		try {
			response.getSerializedPrimaryData();
			Assert.fail("Should not have succeeeded.");
		} catch (ClientResponseSerializationException e) {
			// Pass
		}

		// Try again. Should get ClientAtomicOperationException
		try {
			this.updateRequestSender.update(updateRequest);
			Assert.fail();
		} catch (ClientAtomicOperationException e) {
			List<ModelKey> missingKeys = e.getMissingKeys();
			Assert.assertTrue(missingKeys.contains(template.getModelKey()));
		} catch (Exception e) {
			Assert.fail();
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

		Assert.assertTrue(response.getSuccess());

		// Get Serialized Primary Data
		try {
			SimpleUpdateResponse<T> simpleUpdateResponse = response.getSerializedPrimaryData();

			Collection<T> successfulModels = simpleUpdateResponse.getModels();
			Assert.assertFalse(successfulModels.isEmpty());
			Assert.assertTrue(successfulModels.contains(template));

			Collection<ModelKey> missingKeys = simpleUpdateResponse.getMissingKeys();
			Assert.assertTrue(missingKeys.contains(unavailableTemplate.getModelKey()));
		} catch (ClientResponseSerializationException e) {
			Assert.fail("Should parse fine.");
		}

		// ClientUpdateService
		try {
			SimpleUpdateResponse<T> simpleUpdateResponse = this.updateRequestSender.update(updateRequest);

			Collection<T> successfulModels = simpleUpdateResponse.getModels();
			Assert.assertFalse(successfulModels.isEmpty());
			Assert.assertTrue(successfulModels.contains(template));

			Collection<ModelKey> missingKeys = simpleUpdateResponse.getMissingKeys();
			Assert.assertTrue(missingKeys.contains(unavailableTemplate.getModelKey()));
		} catch (ClientAtomicOperationException e) {
			Assert.fail("Is not an atomic operation.");
		} catch (Exception e) {
			Assert.fail();
			throw e;
		}

	}

	// TODO: Test for attribute failures.

}
