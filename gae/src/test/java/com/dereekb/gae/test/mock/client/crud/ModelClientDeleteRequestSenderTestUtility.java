package com.dereekb.gae.test.mock.client.crud;

import java.util.Collection;

import org.junit.Assert;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.request.ClientDeleteRequest;
import com.dereekb.gae.client.api.model.crud.request.impl.ClientDeleteRequestImpl;
import com.dereekb.gae.client.api.model.crud.response.ClientDeleteResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.impl.DeleteRequestImpl;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

/**
 * Test utility for {@link ClientDeleteRequestSender}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelClientDeleteRequestSenderTestUtility<T extends MutableUniqueModel> {

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientDeleteRequestSender<T> deleteRequestSender;

	public ModelClientDeleteRequestSenderTestUtility(ClientDeleteRequestSender<T> deleteRequestSender,
	        TestModelGenerator<T> testModelGenerator) {
		this.deleteRequestSender = deleteRequestSender;
		this.testModelGenerator = testModelGenerator;
	}

	public void testMockDeleteRequestReturnModels(ClientRequestSecurity security) throws ClientRequestFailureException {

		T template = this.testModelGenerator.generate();

		boolean returnModels = true;

		DeleteRequest deleteRequest = new DeleteRequestImpl(template);
		ClientDeleteRequest clientDeleteRequest = new ClientDeleteRequestImpl(deleteRequest, returnModels);
		SerializedClientApiResponse<ClientDeleteResponse<T>> response = this.deleteRequestSender
		        .sendRequest(clientDeleteRequest, security);

		ClientDeleteResponse<T> deleteResponse = response.getSerializedResponse();
		Collection<T> models = deleteResponse.getModels();

		Assert.assertFalse(models.isEmpty());

		// Delete again.
		template = this.testModelGenerator.generate();

		deleteRequest = new DeleteRequestImpl(template);
		clientDeleteRequest = new ClientDeleteRequestImpl(deleteRequest, returnModels);

		ClientDeleteResponse<T> simpleDeleteResponse = this.deleteRequestSender.delete(clientDeleteRequest);
		Assert.assertFalse(simpleDeleteResponse.getModels().isEmpty());
	}

}
