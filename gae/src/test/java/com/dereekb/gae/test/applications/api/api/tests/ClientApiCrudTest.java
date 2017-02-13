package com.dereekb.gae.test.applications.api.api.tests;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.test.applications.api.ClientApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.client.ModelClientCreateRequestSenderTestUtility;
import com.dereekb.gae.test.applications.api.client.ModelClientDeleteRequestSenderTestUtility;
import com.dereekb.gae.test.applications.api.client.ModelClientReadRequestSenderTestUtility;
import com.dereekb.gae.test.applications.api.client.ModelClientUpdateRequestSenderTestUtility;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class ClientApiCrudTest<T extends MutableUniqueModel> extends ClientApiApplicationTestContext {

	private TestModelGenerator<T> testModelGenerator;

	private ClientCreateRequestSender<T> createRequestSender;
	private ClientReadRequestSender<T> readRequestSender;
	private ClientUpdateRequestSender<T> updateRequestSender;
	private ClientDeleteRequestSender<T> deleteRequestSender;

	private ModelClientCreateRequestSenderTestUtility<T> createRequestUtility;
	private ModelClientReadRequestSenderTestUtility<T> readRequestUtility;
	private ModelClientUpdateRequestSenderTestUtility<T> updateRequestUtility;
	private ModelClientDeleteRequestSenderTestUtility<T> deleteRequestUtility;

	public TestModelGenerator<T> getTestLoginGenerator() {
		return this.testModelGenerator;
	}

	public void setTestModelGenerator(TestModelGenerator<T> testModelGenerator) {
		this.testModelGenerator = testModelGenerator;
	}

	public ClientCreateRequestSender<T> getCreateRequestSender() {
		return this.createRequestSender;
	}

	public void setCreateRequestSender(ClientCreateRequestSender<T> createRequestSender) {
		this.createRequestSender = createRequestSender;
	}

	public ClientReadRequestSender<T> getReadRequestSender() {
		return this.readRequestSender;
	}

	public void setReadRequestSender(ClientReadRequestSender<T> readRequestSender) {
		this.readRequestSender = readRequestSender;
	}

	public ClientUpdateRequestSender<T> getUpdateRequestSender() {
		return this.updateRequestSender;
	}

	public void setUpdateRequestSender(ClientUpdateRequestSender<T> updateRequestSender) {
		this.updateRequestSender = updateRequestSender;
	}

	public ClientDeleteRequestSender<T> getDeleteRequestSender() {
		return this.deleteRequestSender;
	}

	public void setDeleteRequestSender(ClientDeleteRequestSender<T> deleteRequestSender) {
		this.deleteRequestSender = deleteRequestSender;
	}

	// MARK: Utilities
	@Before
	public void setUtilities() {
		if (this.createRequestSender != null) {
			this.createRequestUtility = new ModelClientCreateRequestSenderTestUtility<T>(this.createRequestSender,
			        this.testModelGenerator);
		}

		if (this.readRequestSender != null) {
			this.readRequestUtility = new ModelClientReadRequestSenderTestUtility<T>(this.readRequestSender,
			        this.testModelGenerator);
		}

		if (this.updateRequestSender != null) {
			this.updateRequestUtility = new ModelClientUpdateRequestSenderTestUtility<T>(this.updateRequestSender,
			        this.testModelGenerator);
		}

		if (this.deleteRequestSender != null) {
			this.deleteRequestUtility = new ModelClientDeleteRequestSenderTestUtility<T>(this.deleteRequestSender,
			        this.testModelGenerator);
		}
	}

	// MARK: Create Tests
	@Test
	@Ignore
	public void testSystemClientCreateIsUnavailable() throws Exception {
		if (this.createRequestUtility != null) {
			this.createRequestUtility.testCreateIsUnavailableRequest(ClientRequestSecurityImpl.systemSecurity());
		}
	}

	// MARK: Read Tests
	@Test
	public void testSystemModelClientReadRequest() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testMockReadRequest(ClientRequestSecurityImpl.systemSecurity());
		}
	}

	@Test
	public void testNonAtomicSystemModelClientReadRequest() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testNonAtomicSystemReadRequest(ClientRequestSecurityImpl.systemSecurity());
		}
	}

	@Test
	public void testAtomicSystemModelClientReadRequest() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testAtomicSystemReadRequestFailures(ClientRequestSecurityImpl.systemSecurity());
		}
	}

	// MARK: Update Tests
	@Test
	public void testAtomicSystemModelClientUpdateRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockUpdateRequest(ClientRequestSecurityImpl.systemSecurity());
		}
	}

	@Test
	public void testAtomicSystemModelClientUnavailableRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility
			        .testMockAtomicUnavailableUpdateRequest(ClientRequestSecurityImpl.systemSecurity());
		}
	}

	@Test
	public void testNonAtomicSystemModelClientUnavailableRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility
			        .testMockNonAtomicUnavailableUpdateRequest(ClientRequestSecurityImpl.systemSecurity());
		}
	}

	// MARK: Delete Tests
	@Test
	public void testMockDeleteRequestReturnModels() throws Exception {
		if (this.deleteRequestUtility != null) {
			this.deleteRequestUtility.testMockDeleteRequestReturnModels(ClientRequestSecurityImpl.systemSecurity());
		}
	}

}
