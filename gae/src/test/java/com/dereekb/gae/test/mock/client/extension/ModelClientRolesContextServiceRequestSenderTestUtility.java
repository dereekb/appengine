package com.dereekb.gae.test.mock.client.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;

import com.dereekb.gae.client.api.auth.model.ClientModelRolesContextServiceRequestSender;
import com.dereekb.gae.client.api.auth.model.ClientModelRolesLoginTokenContextResponse;
import com.dereekb.gae.client.api.auth.model.ClientModelRolesResponseData;
import com.dereekb.gae.client.api.auth.model.impl.ClientModelRolesLoginTokenContextRequestImpl;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.auth.controller.model.ApiLoginTokenModelContextType;
import com.dereekb.gae.web.api.auth.controller.model.impl.ApiLoginTokenModelContextTypeImpl;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Test utility for {@link ClientModelRolesContextServiceRequestSender}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelClientRolesContextServiceRequestSenderTestUtility<T extends MutableUniqueModel> {

	private boolean shouldHaveRoles = false;

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientModelRolesContextServiceRequestSender modelRolesContextRequestSender;

	public ModelClientRolesContextServiceRequestSenderTestUtility(
	        ClientModelRolesContextServiceRequestSender modelRolesContextRequestSender,
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

		ClientModelRolesLoginTokenContextRequestImpl request = new ClientModelRolesLoginTokenContextRequestImpl();

		List<ApiLoginTokenModelContextType> contextTypes = new ArrayList<ApiLoginTokenModelContextType>();

		ApiLoginTokenModelContextType contextType = ApiLoginTokenModelContextTypeImpl.fromKeyed(type, model);

		contextTypes.add(contextType);

		request.setAtomic(true);
		request.setMakeContext(true);
		request.setIncludeRoles(true);
		request.setRequestedContexts(contextTypes);

		ClientModelRolesLoginTokenContextResponse response = this.modelRolesContextRequestSender
		        .getContextForModels(request, requestSecurity);

		LoginTokenPair pair = response.getLoginTokenPair();

		Assert.assertNotNull(pair);

		ClientModelRolesResponseData responseData = response.getRolesData();

		Assert.assertNotNull(responseData);

		Map<ModelKey, Set<String>> roles = responseData.getRolesForType(type);

		if (this.shouldHaveRoles) {
			Assert.assertTrue(roles.containsKey(model.getModelKey()));
		}

	}

	public void testSystemClientReadModelRolesForSingleModelAtomicFailsOnMissing(ClientRequestSecurity requestSecurity) {
		String type = this.testModelGenerator.getModelType();

		ModelKey nonExistingModel = this.testModelGenerator.generateKey();

		ClientModelRolesLoginTokenContextRequestImpl request = new ClientModelRolesLoginTokenContextRequestImpl();

		List<ApiLoginTokenModelContextType> contextTypes = new ArrayList<ApiLoginTokenModelContextType>();

		ApiLoginTokenModelContextType contextType = ApiLoginTokenModelContextTypeImpl.fromKeyed(type, nonExistingModel);

		contextTypes.add(contextType);

		request.setAtomic(true);
		request.setMakeContext(true);
		request.setIncludeRoles(true);
		request.setRequestedContexts(contextTypes);

		try {
			this.modelRolesContextRequestSender.getContextForModels(request, requestSecurity);
			Assert.fail();
		} catch (ClientAtomicOperationException e) {
			List<ModelKey> missing = e.getMissingKeys();
			Assert.assertFalse(missing.isEmpty());
			Assert.assertTrue(missing.contains(nonExistingModel));
			Assert.assertTrue(e.getModelType().equals(type));
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

		ClientModelRolesLoginTokenContextRequestImpl request = new ClientModelRolesLoginTokenContextRequestImpl();

		List<ApiLoginTokenModelContextType> contextTypes = new ArrayList<ApiLoginTokenModelContextType>();

		ApiLoginTokenModelContextType contextType = ApiLoginTokenModelContextTypeImpl.fromKeyed(type, models);

		contextTypes.add(contextType);

		request.setAtomic(true);
		request.setMakeContext(true);
		request.setIncludeRoles(true);
		request.setRequestedContexts(contextTypes);

		ClientModelRolesLoginTokenContextResponse response = this.modelRolesContextRequestSender
		        .getContextForModels(request, requestSecurity);

		LoginTokenPair pair = response.getLoginTokenPair();

		Assert.assertNotNull(pair);

		ClientModelRolesResponseData responseData = response.getRolesData();

		Assert.assertNotNull(responseData);

		Map<ModelKey, Set<String>> roles = responseData.getRolesForType(type);

		if (this.shouldHaveRoles) {

			for (T model : models) {
				Assert.assertTrue(roles.containsKey(model.getModelKey()));
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

		ClientModelRolesLoginTokenContextRequestImpl request = new ClientModelRolesLoginTokenContextRequestImpl();

		List<ApiLoginTokenModelContextType> contextTypes = new ArrayList<ApiLoginTokenModelContextType>();

		ApiLoginTokenModelContextType contextType = ApiLoginTokenModelContextTypeImpl.fromKeyed(type, keys);

		contextTypes.add(contextType);

		request.setAtomic(true);
		request.setMakeContext(true);
		request.setIncludeRoles(true);
		request.setRequestedContexts(contextTypes);

		try {
			this.modelRolesContextRequestSender.getContextForModels(request, requestSecurity);
			Assert.fail();
		} catch (ClientAtomicOperationException e) {
			List<ModelKey> missing = e.getMissingKeys();
			Assert.assertFalse(missing.isEmpty());
			Assert.assertTrue(missing.contains(nonExistingModel));
			Assert.assertTrue(e.getModelType().equals(type));
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

		ClientModelRolesLoginTokenContextRequestImpl request = new ClientModelRolesLoginTokenContextRequestImpl();

		List<ApiLoginTokenModelContextType> contextTypes = new ArrayList<ApiLoginTokenModelContextType>();

		ApiLoginTokenModelContextType contextType = ApiLoginTokenModelContextTypeImpl.fromKeyed(type, keys);

		contextTypes.add(contextType);

		request.setAtomic(false);	// Not atomic
		request.setMakeContext(true);
		request.setIncludeRoles(true);
		request.setRequestedContexts(contextTypes);

		try {
			ClientModelRolesLoginTokenContextResponse response = this.modelRolesContextRequestSender
			        .getContextForModels(request, requestSecurity);

			ClientModelRolesResponseData responseData = response.getRolesData();
			Assert.assertNotNull(responseData);

			Map<ModelKey, Set<String>> roles = responseData.getRolesForType(type);
			Assert.assertFalse(roles.containsKey(nonExistingModel.getModelKey()));
		} catch (ClientRequestFailureException e) {
			Assert.fail();
		}
	}

}
