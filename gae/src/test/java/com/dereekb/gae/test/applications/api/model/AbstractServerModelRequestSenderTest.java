package com.dereekb.gae.test.applications.api.model;

import org.junit.Before;
import org.junit.Test;

import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.EncodedLoginTokenImpl;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.test.mock.client.crud.ModelClientCreateRequestSenderTestUtility;
import com.dereekb.gae.test.mock.client.crud.ModelClientDeleteRequestSenderTestUtility;
import com.dereekb.gae.test.mock.client.crud.ModelClientReadRequestSenderTestUtility;
import com.dereekb.gae.test.mock.client.crud.ModelClientUpdateRequestSenderTestUtility;
import com.dereekb.gae.test.mock.client.extension.ModelClientLinkRequestSenderTestUtility;
import com.dereekb.gae.test.mock.client.extension.ModelClientQueryRequestSenderTestUtility;

public abstract class AbstractServerModelRequestSenderTest<T extends MutableUniqueModel> extends AbstractModelRequestSenderTest<T> {

	private boolean canCreateModel = true;

	private ModelClientCreateRequestSenderTestUtility<T> createRequestUtility;
	private ModelClientReadRequestSenderTestUtility<T> readRequestUtility;
	private ModelClientUpdateRequestSenderTestUtility<T> updateRequestUtility;
	private ModelClientDeleteRequestSenderTestUtility<T> deleteRequestUtility;

	// Search
	private ModelClientQueryRequestSenderTestUtility<T> queryRequestUtility;

	// Link
	private ModelClientLinkRequestSenderTestUtility<T> linkRequestUtility;

	public boolean isCanCreateModel() {
		return this.canCreateModel;
	}

	public void setCanCreateModel(boolean canCreateModel) {
		this.canCreateModel = canCreateModel;
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

		if (this.queryRequestSender != null) {
			this.queryRequestUtility = new ModelClientQueryRequestSenderTestUtility<T>(this.queryRequestSender,
			        this.testModelGenerator);
		}
	}

	@Before
	public void generateSystemUser() {
		// Use system account for tests
		this.testLoginTokenContext.generateSystemAdmin();
	}

	public ModelClientCreateRequestSenderTestUtility<T> getCreateRequestUtility() {
		return this.createRequestUtility;
	}

	public ModelClientReadRequestSenderTestUtility<T> getReadRequestUtility() {
		return this.readRequestUtility;
	}

	public ModelClientUpdateRequestSenderTestUtility<T> getUpdateRequestUtility() {
		return this.updateRequestUtility;
	}

	public ModelClientDeleteRequestSenderTestUtility<T> getDeleteRequestUtility() {
		return this.deleteRequestUtility;
	}

	public ModelClientQueryRequestSenderTestUtility<T> getQueryRequestUtility() {
		return this.queryRequestUtility;
	}

	// MARK: Create Tests
	@Test
	public void testSystemClientCreateIsUnavailable() throws Exception {
		if (this.canCreateModel == false && this.createRequestUtility != null) {
			this.createRequestUtility.testCreateIsUnavailableRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemModelClientCreateRequest() throws Exception {
		if (this.canCreateModel == true && this.createRequestUtility != null) {
			this.createRequestUtility.testMockCreateRequest(this.getRequestSecurity());
		}
	}

	// TODO: Add test for invalid create templates.

	// MARK: Read Tests
	@Test
	public void testSystemModelClientReadRequest() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testMockReadRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testNonAtomicModelClientReadRequest() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testNonAtomicReadRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testAtomicModelClientReadRequest() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testAtomicReadRequestFailures(this.getRequestSecurity());
		}
	}

	// MARK: Update Tests
	@Test
	public void testAtomicModelClientUpdateRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockUpdateRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testAtomicModelClientUnavailableRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockAtomicUnavailableUpdateRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testNonAtomicModelClientUnavailableRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockNonAtomicUnavailableUpdateRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testUpdateWithNoIdentifierClientUpdateRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockUpdateRequest(this.getRequestSecurity());
		}
	}

	// TODO: Add test for invalid update templates.

	// MARK: Delete Tests
	@Test
	public void testMockDeleteRequestReturnModels() throws Exception {
		if (this.deleteRequestUtility != null) {
			this.deleteRequestUtility.testMockDeleteRequestReturnModels(this.getRequestSecurity());
		}
	}

	// MARK: Query Tests
	@Test
	public void testSystemClientQuery() throws Exception {
		if (this.queryRequestUtility != null) {
			this.queryRequestUtility.testMockQueryRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientKeysOnlyQuery() throws Exception {
		if (this.queryRequestUtility != null) {
			this.queryRequestUtility.testMockQueryRequestKeysOnly(this.getRequestSecurity());
		}
	}

	// MARK: Link Tests
	@Test
	public void testSystemClientLinkToNonExistantLink() throws Exception {
		if (this.linkRequestUtility != null) {
			this.linkRequestUtility.testSystemClientLinkToNonExistantLink(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientLinkAtomicRequest() throws Exception {
		if (this.linkRequestUtility != null) {
			this.linkRequestUtility.testSystemClientLinkAtomicRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientLinkNonAtomicRequest() throws Exception {
		if (this.linkRequestUtility != null) {
			this.linkRequestUtility.testSystemClientLinkNonAtomicRequest(this.getRequestSecurity());
		}
	}

	// MARK: Utility
	@Override
	public ClientRequestSecurity getRequestSecurity() {
		EncodedLoginToken overrideToken = new EncodedLoginTokenImpl(this.testLoginTokenContext.getToken());
		return new ClientRequestSecurityImpl(overrideToken);
	}

}
