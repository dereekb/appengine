package com.dereekb.gae.test.applications.api;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.EncodedLoginTokenImpl;
import com.dereekb.gae.test.mock.client.crud.MockClientRequestSender;

/**
 * Abstract {@link ApiApplicationTestContext} extension that initializes the
 * mock client request sender.
 *
 * @author dereekb
 * @Deprecated
 *
 */
public abstract class ClientApiApplicationTestContext extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("mockClientRequestSender")
	private MockClientRequestSender mockRequestSender;

	@Override
	@Before
	public void initializeMockRequestSender() {
		this.mockRequestSender.setWebServiceTester(this);
	}

	// MARK: Utility
	public ClientRequestSecurity getRequestSecurity() {
		EncodedLoginToken overrideToken = new EncodedLoginTokenImpl(this.testLoginTokenContext.getToken());
		return new ClientRequestSecurityImpl(overrideToken);
	}

}
