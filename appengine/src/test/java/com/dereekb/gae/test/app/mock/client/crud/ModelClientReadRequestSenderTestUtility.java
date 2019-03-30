package com.dereekb.gae.test.app.mock.client.crud;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientReadRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.request.ClientReadRequest;
import com.dereekb.gae.client.api.model.crud.request.impl.ClientReadRequestImpl;
import com.dereekb.gae.client.api.model.crud.response.SerializedClientReadApiResponse;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.exception.LargeAtomicRequestException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.ModelReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.ReadRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.SimpleReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.crud.controller.ReadController;

/**
 * Test utility for {@link ClientReadRequestSender}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelClientReadRequestSenderTestUtility<T extends UniqueModel> {

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientReadRequestSender<T> readRequestSender;

	public ModelClientReadRequestSenderTestUtility(ClientReadRequestSender<T> readRequestSender,
	        TestModelGenerator<T> testModelGenerator) {
		this.readRequestSender = readRequestSender;
		this.testModelGenerator = testModelGenerator;
	}

	public void testMockReadRequest(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		List<T> logins = this.testModelGenerator.generate(10);

		ReadRequest readRequest = new ModelReadRequest(logins);

		SerializedClientReadApiResponse<T> response = this.readRequestSender.sendRequest(readRequest, security);

		SimpleReadResponse<T> readResponse = response.getSerializedResponse();
		Collection<T> models = readResponse.getModels();

		assertTrue(logins.size() == models.size());
	}

	/**
	 * Test that performs a read request that requests included types.
	 */
	public void testMockReadRequestWithRelated(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		List<T> logins = this.testModelGenerator.generate(10);

		ReadRequest readRequest = new ModelReadRequest(logins);

		ClientReadRequest clientReadRequest = new ClientReadRequestImpl(true, readRequest);
		SerializedClientReadApiResponse<T> response = this.readRequestSender.sendRequest(clientReadRequest, security);

		SimpleReadResponse<T> readResponse = response.getSerializedResponse();
		Collection<T> models = readResponse.getModels();

		assertTrue(logins.size() == models.size());

		// Can't really assert anything here.
		Map<String, ClientApiResponseData> includedData = response.getIncludedData();
		assertNotNull(includedData);
	}

	/**
	 * Tests non atomic read requests.
	 */
	public void testNonAtomicReadRequest(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		List<T> logins = this.testModelGenerator.generate(3);
		List<ModelKey> modelKeys = ModelKey.readModelKeys(logins);

		List<ModelKey> unavailableKeys = new ArrayList<ModelKey>();
		unavailableKeys.add(this.testModelGenerator.generateKey());
		unavailableKeys.add(this.testModelGenerator.generateKey());
		unavailableKeys.add(this.testModelGenerator.generateKey());

		modelKeys.addAll(unavailableKeys);

		ReadRequestOptions options = new ReadRequestOptionsImpl(false);
		ReadRequest readRequest = new KeyReadRequest(modelKeys, options);

		SerializedClientReadApiResponse<T> response = this.readRequestSender.sendRequest(readRequest, security);

		SimpleReadResponse<T> readResponse = response.getSerializedResponse();
		Collection<T> models = readResponse.getModels();
		assertTrue(logins.size() == models.size());

		Collection<ModelKey> responseUnavailableKeys = readResponse.getFailed();
		assertTrue(responseUnavailableKeys.size() == unavailableKeys.size());
	}

	/**
	 * Tests that atomic read requests fail when expected.
	 */
	public void testAtomicReadRequestFailures(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		List<ModelKey> unavailableKeys = new ArrayList<ModelKey>();
		unavailableKeys.add(this.testModelGenerator.generateKey());
		unavailableKeys.add(this.testModelGenerator.generateKey());
		unavailableKeys.add(this.testModelGenerator.generateKey());

		ReadRequestOptions options = new ReadRequestOptionsImpl(true);
		ReadRequest readRequest = new KeyReadRequest(unavailableKeys, options);

		try {
			SerializedClientReadApiResponse<T> response = this.readRequestSender.sendRequest(readRequest, security);
			response.getSerializedResponse();
			fail("Cannot serialize response data with request failure.");
		} catch (ClientRequestFailureException e) {
			// Pass
		}

		try {
			this.readRequestSender.read(readRequest, security);
			fail("Should have thrown an atomic operation exception.");
		} catch (ClientAtomicOperationException e) {

		}
	}

	/**
	 * Tests that reading nothing will result in an exception.
	 */
	public void testReadNothing(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		List<ModelKey> noKeys = new ArrayList<ModelKey>();

		ReadRequestOptions options = new ReadRequestOptionsImpl(true);
		ReadRequest readRequest = new KeyReadRequest(noKeys, options);

		try {
			SimpleReadResponse<T> response = this.readRequestSender.read(readRequest, security);
			Collection<T> models = response.getModels();
			assertTrue(models.isEmpty());
			//fail("Should have failed request.");
		} catch (ClientRequestFailureException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests that attempting to read more than the maximum amount will throw a client error.
	 */
	public void testReadMoreThanMaxThrowsClientError(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		List<ModelKey> tooManyKeys = this.testModelGenerator.generateKeys(ReadController.MAX_KEYS_PER_REQUEST + 10);

		ReadRequestOptions options = new ReadRequestOptionsImpl(true);
		ReadRequest readRequest = new KeyReadRequest(tooManyKeys, options);

		try {
			this.readRequestSender.read(readRequest, security);
			fail("Should have failed request.");
		} catch (LargeAtomicRequestException e) {

		}
	}

	/**
	 * Tests that sending a request with more than the max allowed amount of keys returns a response error.
	 */
	public void testSendReadMoreThanMaxThrowsApiResponse(ClientRequestSecurity security)
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {

		List<ModelKey> tooManyKeys = this.testModelGenerator.generateKeys(ReadController.MAX_KEYS_PER_REQUEST + 10);
		ReadRequestOptions options = new ReadRequestOptionsImpl(true);

		try {
			@SuppressWarnings("unchecked")
			ClientReadRequestSenderImpl<T, ?> impl = (ClientReadRequestSenderImpl<T, ?>)this.readRequestSender;

			ClientRequest request = impl.buildClientRequest(tooManyKeys, options, null);

			SecuredClientApiRequestSender requestSender = impl.getRequestSender();
			ClientApiResponse response = requestSender.sendRequest(request);

			ClientTooMuchInputException.assertNotTooMuchInputException(response);

			fail("Should have failed request.");
		} catch (ClientTooMuchInputException e) {

		}
	}

}
