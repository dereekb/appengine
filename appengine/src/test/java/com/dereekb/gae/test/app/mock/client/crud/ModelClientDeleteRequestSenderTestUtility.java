package com.dereekb.gae.test.app.mock.client.crud;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.exception.ClientTooMuchInputException;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientDeleteRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.request.ClientDeleteRequest;
import com.dereekb.gae.client.api.model.crud.request.impl.ClientDeleteRequestImpl;
import com.dereekb.gae.client.api.model.crud.response.ClientDeleteResponse;
import com.dereekb.gae.client.api.model.exception.LargeAtomicRequestException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.impl.DeleteRequestImpl;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;

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

		assertFalse(models.isEmpty());

		// Delete again.
		template = this.testModelGenerator.generate();

		deleteRequest = new DeleteRequestImpl(template);
		clientDeleteRequest = new ClientDeleteRequestImpl(deleteRequest, returnModels);

		ClientDeleteResponse<T> simpleDeleteResponse = this.deleteRequestSender.delete(clientDeleteRequest, security);
		assertFalse(simpleDeleteResponse.getModels().isEmpty());
	}

	public void testMockDeleteTooManyRequestThrowsClientError(ClientRequestSecurity security) throws ClientRequestFailureException {

		int moreThanMax = EditModelController.MAX_ATOMIC_EDIT_SIZE + 5;

		List<T> tooManyTemplates = this.testModelGenerator.generate(moreThanMax);
		DeleteRequest deleteRequest = new DeleteRequestImpl(tooManyTemplates);

		try {
			this.deleteRequestSender.delete(deleteRequest, security);
			fail("Should have failed request.");
		} catch (LargeAtomicRequestException e) {

		}
	}

	public void testSendMockDeleteTooManyRequestThrowsApiResponse(ClientRequestSecurity security) throws ClientRequestFailureException {

		int moreThanMax = EditModelController.MAX_ATOMIC_EDIT_SIZE + 5;

		List<T> tooManyTemplates = this.testModelGenerator.generate(moreThanMax);
		List<ModelKey> modelKeys = ModelKey.readModelKeys(tooManyTemplates);
		DeleteRequest deleteRequest = new DeleteRequestImpl(tooManyTemplates);

		try {
			@SuppressWarnings("unchecked")
			ClientDeleteRequestSenderImpl<T, ?> impl = (ClientDeleteRequestSenderImpl<T, ?>)this.deleteRequestSender;

			ClientRequest request = impl.buildClientRequest(modelKeys, deleteRequest.getOptions(), false);

			SecuredClientApiRequestSender requestSender = impl.getRequestSender();
			ClientApiResponse response = requestSender.sendRequest(request);

			ClientTooMuchInputException.assertNotTooMuchInputException(response);

			fail("Should have failed request.");
		} catch (ClientTooMuchInputException e) {

		}
	}

}
