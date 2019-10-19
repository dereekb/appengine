package com.dereekb.gae.test.app.mock.client.extension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesResponse;
import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesResponseData;
import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesServiceRequestSender;
import com.dereekb.gae.client.api.auth.model.roles.impl.ClientModelRolesRequestImpl;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesTypedKeysRequest;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesTypedKeysRequestImpl;

/**
 * Test utility for {@link ClientModelRolesServiceRequestSender}.
 * <p>
 * Failing any of these tests generally means that the roles are probably not
 * configured properly.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelClientRolesServiceRequestSenderTestUtility<T extends MutableUniqueModel> {

	private boolean shouldHaveRoles = false;

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientModelRolesServiceRequestSender modelRolesContextRequestSender;

	public ModelClientRolesServiceRequestSenderTestUtility(
	        ClientModelRolesServiceRequestSender modelRolesContextRequestSender,
	        TestModelGenerator<T> testModelGenerator) {
		super();
		this.testModelGenerator = testModelGenerator;
		this.modelRolesContextRequestSender = modelRolesContextRequestSender;
	}

	// MARK: Tests
	public void testSystemClientReadModelRolesForSingleModelAtomic(ClientRequestSecurity requestSecurity)
	        throws Exception {
		String type = this.testModelGenerator.getModelType();

		T model = this.testModelGenerator.generate();

		ClientModelRolesRequestImpl request = new ClientModelRolesRequestImpl();

		List<ApiModelRolesTypedKeysRequest> contextTypes = new ArrayList<ApiModelRolesTypedKeysRequest>();

		ApiModelRolesTypedKeysRequest contextType = ApiModelRolesTypedKeysRequestImpl.fromKeyed(type, model);

		contextTypes.add(contextType);

		request.setAtomic(true);
		request.setRequestedContexts(contextTypes);

		ClientModelRolesResponse response = this.modelRolesContextRequestSender.sendRequest(request, requestSecurity)
		        .getSerializedResponse();

		ClientModelRolesResponseData responseData = response.getRolesData();

		assertNotNull(responseData);

		Map<ModelKey, Set<String>> roles = responseData.getRolesForType(type);

		if (this.shouldHaveRoles) {
			assertTrue(roles.containsKey(model.getModelKey()));
		}

	}

	public void testSystemClientReadModelRolesForSingleModelAtomicFailsOnMissing(ClientRequestSecurity requestSecurity) {
		String type = this.testModelGenerator.getModelType();

		ModelKey nonExistingModel = this.testModelGenerator.generateKey();

		ClientModelRolesRequestImpl request = new ClientModelRolesRequestImpl();

		List<ApiModelRolesTypedKeysRequest> contextTypes = new ArrayList<ApiModelRolesTypedKeysRequest>();

		ApiModelRolesTypedKeysRequest contextType = ApiModelRolesTypedKeysRequestImpl.fromKeyed(type, nonExistingModel);

		contextTypes.add(contextType);

		request.setAtomic(true);
		request.setRequestedContexts(contextTypes);

		try {
			this.modelRolesContextRequestSender.getRolesForModels(request, requestSecurity);
			fail();
		} catch (ClientAtomicOperationException e) {
			List<ModelKey> missing = e.getMissingKeys();
			assertFalse(missing.isEmpty());
			assertTrue(missing.contains(nonExistingModel));
			assertTrue(e.getModelType().equals(type));
		} catch (ClientRequestFailureException e) {

		}
	}

	public void testSystemClientReadModelRolesForMultipleModelsAtomic(ClientRequestSecurity requestSecurity)
	        throws ClientIllegalArgumentException,
	            ClientAtomicOperationException,
	            ClientRequestFailureException {
		String type = this.testModelGenerator.getModelType();

		int count = 3;
		List<T> models = this.testModelGenerator.generate(count);

		ClientModelRolesRequestImpl request = new ClientModelRolesRequestImpl();

		List<ApiModelRolesTypedKeysRequest> contextTypes = new ArrayList<ApiModelRolesTypedKeysRequest>();

		ApiModelRolesTypedKeysRequest contextType = ApiModelRolesTypedKeysRequestImpl.fromKeyed(type, models);

		contextTypes.add(contextType);

		request.setAtomic(true);
		request.setRequestedContexts(contextTypes);

		ClientModelRolesResponse response = this.modelRolesContextRequestSender.sendRequest(request, requestSecurity)
		        .getSerializedResponse();

		ClientModelRolesResponseData responseData = response.getRolesData();

		assertNotNull(responseData);

		Map<ModelKey, Set<String>> roles = responseData.getRolesForType(type);

		if (this.shouldHaveRoles) {

			for (T model : models) {
				assertTrue(roles.containsKey(model.getModelKey()));
			}
		}

	}

	public void testSystemClientReadModelRolesForMultipleModelsAtomicFailsOnMissing(ClientRequestSecurity requestSecurity) {
		String type = this.testModelGenerator.getModelType();

		int count = 3;
		List<T> models = this.testModelGenerator.generate(count);

		List<ModelKey> keys = ModelKey.readModelKeys(models);

		ModelKey nonExistingModel = this.testModelGenerator.generateKey();
		keys.add(nonExistingModel);

		ClientModelRolesRequestImpl request = new ClientModelRolesRequestImpl();

		List<ApiModelRolesTypedKeysRequest> contextTypes = new ArrayList<ApiModelRolesTypedKeysRequest>();

		ApiModelRolesTypedKeysRequest contextType = ApiModelRolesTypedKeysRequestImpl.fromKeyed(type, keys);

		contextTypes.add(contextType);

		request.setAtomic(true);
		request.setRequestedContexts(contextTypes);

		try {
			this.modelRolesContextRequestSender.getRolesForModels(request, requestSecurity);
			fail();
		} catch (ClientAtomicOperationException e) {
			List<ModelKey> missing = e.getMissingKeys();
			assertFalse(missing.isEmpty());
			assertTrue(missing.contains(nonExistingModel));
			assertTrue(e.getModelType().equals(type));
		} catch (ClientRequestFailureException e) {

		}
	}

	public void testSystemClientReadModelRolesForMultipleModelsNonAtomicWithMissing(ClientRequestSecurity requestSecurity) {
		String type = this.testModelGenerator.getModelType();

		int count = 3;
		List<T> models = this.testModelGenerator.generate(count);

		List<ModelKey> keys = ModelKey.readModelKeys(models);

		ModelKey nonExistingModel = this.testModelGenerator.generateKey();
		keys.add(nonExistingModel);

		ClientModelRolesRequestImpl request = new ClientModelRolesRequestImpl();

		List<ApiModelRolesTypedKeysRequest> contextTypes = new ArrayList<ApiModelRolesTypedKeysRequest>();

		ApiModelRolesTypedKeysRequest contextType = ApiModelRolesTypedKeysRequestImpl.fromKeyed(type, keys);

		contextTypes.add(contextType);

		request.setAtomic(false);	// Not atomic
		request.setRequestedContexts(contextTypes);

		try {
			ClientModelRolesResponse response = this.modelRolesContextRequestSender
			        .sendRequest(request, requestSecurity).getSerializedResponse();

			ClientModelRolesResponseData responseData = response.getRolesData();
			assertNotNull(responseData);

			Map<ModelKey, Set<String>> roles = responseData.getRolesForType(type);
			assertFalse(roles.containsKey(nonExistingModel.getModelKey()));
		} catch (ClientRequestFailureException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
