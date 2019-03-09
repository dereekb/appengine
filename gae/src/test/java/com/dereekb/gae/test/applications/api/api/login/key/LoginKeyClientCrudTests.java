package com.dereekb.gae.test.applications.api.api.login.key;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.impl.CreateRequestImpl;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.test.applications.api.api.tests.client.ClientApiCrudTest;
import com.dereekb.gae.test.mock.auth.MockKeyLoginControllerUtility;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.googlecode.objectify.Key;

public class LoginKeyClientCrudTests extends ClientApiCrudTest<LoginKey> {

	@Override
	@Autowired
	@Qualifier("loginKeyTestModelGenerator")
	public void setTestModelGenerator(TestModelGenerator<LoginKey> testLoginKeyGenerator) {
		super.setTestModelGenerator(testLoginKeyGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyClientCreateRequestSender")
	public void setCreateRequestSender(ClientCreateRequestSender<LoginKey> requestSender) {
		super.setCreateRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyClientReadRequestSender")
	public void setReadRequestSender(ClientReadRequestSender<LoginKey> requestSender) {
		super.setReadRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyClientUpdateRequestSender")
	public void setUpdateRequestSender(ClientUpdateRequestSender<LoginKey> requestSender) {
		super.setUpdateRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyClientDeleteRequestSender")
	public void setDeleteRequestSender(ClientDeleteRequestSender<LoginKey> requestSender) {
		super.setDeleteRequestSender(requestSender);
	}

	private MockKeyLoginControllerUtility utility = new MockKeyLoginControllerUtility(this);

	@Before
	public void setupTestsLoginKeyApi() throws Exception {
		// Run all tests as a normal user.
		this.testLoginTokenContext.generateLogin("NORMAL_USER");
		this.utility.assertMockEnableApiKey();
	}

	// MARK: Test Overrides
	@Override
	@Test
	public void testSystemModelClientCreateRequest() throws Exception {
		ClientCreateRequestSender<LoginKey> createRequestSender = this.getCreateRequestSender();
		ClientRequestSecurity security = this.getRequestSecurity();

		LoginKey key = new LoginKey();

		key.setName("TEST CREATE KEY");
		key.setVerification("VerificationCode");

		CreateRequest<LoginKey> createRequest = new CreateRequestImpl<LoginKey>(key);
		SerializedClientApiResponse<CreateResponse<LoginKey>> response = createRequestSender.sendRequest(createRequest,
		        security);

		CreateResponse<LoginKey> responseKey = response.getSerializedPrimaryData();
		Collection<LoginKey> apiKeys = responseKey.getModels();

		Assert.assertFalse(apiKeys.isEmpty());
	}

	@Test
	public void testSystemModelClientInvalidCreateRequest() throws Exception {
		ClientCreateRequestSender<LoginKey> createRequestSender = this.getCreateRequestSender();
		ClientRequestSecurity security = this.getRequestSecurity();

		LoginKey key = new LoginKey();

		key.setName("BAD KEY");
		key.setVerification("VerificationCode");
		key.setLoginPointer(Key.create(LoginPointer.class, "POINTER"));

		CreateRequest<LoginKey> createRequest = new CreateRequestImpl<LoginKey>(key);

		try {
			createRequestSender.create(createRequest, security);
			Assert.fail();
		} catch (ClientKeyedInvalidAttributeException attributeException) {
			// Pass
		}

	}

}
