package com.dereekb.gae.test.app.web.api.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
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
import com.dereekb.gae.server.auth.model.key.search.query.LoginKeyQueryInitializer.ObjectifyLoginKeyQuery;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.test.app.mock.auth.MockKeyLoginControllerUtility;
import com.dereekb.gae.test.app.mock.client.tests.AbstractServerModelRequestSenderTests;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.googlecode.objectify.Key;

/**
 * {@link AbstractServerModelRequestSenderTests} tests for {@link LoginKey}.
 * <p>
 * These tests are available for other applications, but mainly serve as tests to capture
 * any potential API changes/bugs.
 *
 * @author dereekb
 *
 */
public class LoginKeyClientCrudTests extends AbstractServerModelRequestSenderTests<LoginKey> {

	@Autowired
	@Qualifier("loginKeyRegistry")
	private ObjectifyRegistry<LoginKey> registry;

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

		CreateResponse<LoginKey> responseKey = response.getSerializedResponse();
		Collection<LoginKey> apiKeys = responseKey.getModels();

		assertFalse(apiKeys.isEmpty());
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
			fail();
		} catch (ClientKeyedInvalidAttributeException attributeException) {
			// Pass
		}

	}

	// MARK: Query Tests
	@Test
	public void testQueryingLoginPointer() {
		String pointerId = "POINTER_KEY";
		Key<LoginPointer> pointerKey = Key.create(LoginPointer.class, pointerId);
		List<LoginKey> keys = this.getTestModelGenerator().generate(6);
		List<LoginKey> pointers = new ArrayList<LoginKey>();

		// 3 of the LoginKeys should be associated with the pointer now.
		for (int i = 0; i < keys.size(); i += 2) {
			LoginKey key = keys.get(i);
			key.setLoginPointer(pointerKey);
			pointers.add(key);
		}

		this.registry.update(keys);

		ObjectifyLoginKeyQuery keyQueryConfig = new ObjectifyLoginKeyQuery();
		keyQueryConfig.setLoginPointer(pointerKey);

		ObjectifyQueryRequestBuilder<LoginKey> query = this.registry.makeQuery();
		keyQueryConfig.configure(query);

		List<LoginKey> results = query.buildExecutableQuery().queryModels();
		Assert.assertFalse(results.isEmpty());
		Assert.assertTrue(results.size() == pointers.size());
		Assert.assertTrue(pointers.containsAll(results));
	}

}
