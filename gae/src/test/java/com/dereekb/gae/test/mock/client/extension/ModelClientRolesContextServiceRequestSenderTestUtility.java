package com.dereekb.gae.test.mock.client.extension;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.dereekb.gae.client.api.auth.model.ClientModelRolesContextServiceRequestSender;
import com.dereekb.gae.client.api.auth.model.ClientModelRolesLoginTokenContextResponse;
import com.dereekb.gae.client.api.auth.model.ClientModelRolesResponseData;
import com.dereekb.gae.client.api.auth.model.impl.ClientModelRolesLoginTokenContextRequestImpl;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
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

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientModelRolesContextServiceRequestSender modelRolesContextRequestSender;

	public ModelClientRolesContextServiceRequestSenderTestUtility(ClientModelRolesContextServiceRequestSender modelRolesContextRequestSender,
	        TestModelGenerator<T> testModelGenerator) {
		super();
		this.testModelGenerator = testModelGenerator;
		this.modelRolesContextRequestSender = modelRolesContextRequestSender;
	}

	// MARK: Tests
	public void testSystemClientReadModelRolesForSingleModelAtomic(ClientRequestSecurity security) throws Exception {
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
		        .getContextForModels(request, security);

		LoginTokenPair pair = response.getLoginTokenPair();

		Assert.assertNotNull(pair);

		ClientModelRolesResponseData responseData = response.getRolesData();

		Assert.assertNotNull(responseData);

	}

}
