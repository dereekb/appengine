package com.dereekb.gae.test.applications.api.client;

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
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.impl.UpdateRequestImpl;
import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.UpdateRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

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
		SerializedClientApiResponse<UpdateResponse<T>> response = this.updateRequestSender.sendRequest(updateRequest,
		        security);

		UpdateResponse<T> updateResponse = response.getSerializedPrimaryData();
		Collection<T> models = updateResponse.getUpdatedModels();

		Assert.assertFalse(models.isEmpty());
	}

}
