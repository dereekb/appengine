package com.dereekb.gae.test.applications.api.api.tests;

import org.junit.Before;
import org.junit.Test;

import com.dereekb.gae.client.api.exception.ClientAuthenticationException;
import com.dereekb.gae.client.api.exception.ClientConnectionException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.test.applications.api.ClientApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.client.ModelClientReadRequestSenderTestUtility;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class ClientApiCrudTest<T extends UniqueModel> extends ClientApiApplicationTestContext {

	private TestModelGenerator<T> testLoginGenerator;

	private ClientReadRequestSender<T> readRequestSender;

	private ModelClientReadRequestSenderTestUtility<T> readRequestUtility;

	public TestModelGenerator<T> getTestLoginGenerator() {
		return this.testLoginGenerator;
	}

	public void setTestModelGenerator(TestModelGenerator<T> testLoginGenerator) {
		this.testLoginGenerator = testLoginGenerator;
	}

	public ClientReadRequestSender<T> getReadRequestSender() {
		return this.readRequestSender;
	}

	public void setReadRequestSender(ClientReadRequestSender<T> readRequestSender) {
		this.readRequestSender = readRequestSender;
	}

	@Before
	public void setUtilities() {
		if (this.readRequestSender != null) {
			this.readRequestUtility = new ModelClientReadRequestSenderTestUtility<T>(this.readRequestSender,
			        this.testLoginGenerator);
		}
	}

	// MARK: Tests
	@Test
	public void testSystemLoginClientReadRequest()
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testMockReadRequest(ClientRequestSecurityImpl.systemSecurity());
		}
	}

	@Test
	public void testNonAtomicSystemLoginClientReadRequest()
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testNonAtomicSystemReadRequest(ClientRequestSecurityImpl.systemSecurity());
		}
	}

	@Test
	public void testAtomicSystemLoginClientReadRequest()
	        throws NotClientApiResponseException,
	            ClientConnectionException,
	            ClientAuthenticationException,
	            ClientRequestFailureException {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testAtomicSystemReadRequest(ClientRequestSecurityImpl.systemSecurity());
		}
	}

}
