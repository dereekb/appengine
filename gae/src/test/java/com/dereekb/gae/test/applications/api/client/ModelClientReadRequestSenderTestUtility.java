package com.dereekb.gae.test.applications.api.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.ModelReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.ReadRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.SimpleReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

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

		SerializedClientApiResponse<SimpleReadResponse<T>> response = this.readRequestSender.sendRequest(readRequest,
		        security);

		SimpleReadResponse<T> readResponse = response.getSerializedPrimaryData();
		Collection<T> models = readResponse.getModels();

		Assert.assertTrue(logins.size() == models.size());
	}

	/**
	 * Tests non atomic read requests.
	 */
	public void testNonAtomicSystemReadRequest(ClientRequestSecurity security)
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

		SerializedClientApiResponse<SimpleReadResponse<T>> response = this.readRequestSender.sendRequest(readRequest,
		        security);

		SimpleReadResponse<T> readResponse = response.getSerializedPrimaryData();
		Collection<T> models = readResponse.getModels();
		Assert.assertTrue(logins.size() == models.size());

		Collection<ModelKey> responseUnavailableKeys = readResponse.getFailed();
		Assert.assertTrue(responseUnavailableKeys.size() == unavailableKeys.size());
	}

	/**
	 * Tests that atomic read requests fail when expected.
	 */
	public void testAtomicSystemReadRequestFailures(ClientRequestSecurity security)
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
			SerializedClientApiResponse<SimpleReadResponse<T>> response = this.readRequestSender
			        .sendRequest(readRequest, security);
			response.getSerializedPrimaryData();
			Assert.fail("Cannot serialize response data with request failure.");
		} catch (ClientResponseSerializationException e) {

		}

		try {
			this.readRequestSender.read(readRequest);
			Assert.fail("Should have thrown an atomic operation exception.");
		} catch (ClientAtomicOperationException e) {

		}
	}

}
