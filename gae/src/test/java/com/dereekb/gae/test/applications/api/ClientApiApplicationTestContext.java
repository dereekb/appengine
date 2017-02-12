package com.dereekb.gae.test.applications.api;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.test.applications.api.client.MockClientRequestSender;

/**
 * Abstract {@link ApiApplicationTestContext} extension that initializes the
 * mock client request sender.
 * 
 * @author dereekb
 *
 */
public abstract class ClientApiApplicationTestContext extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("mockClientRequestSender")
	private MockClientRequestSender mockRequestSender;

	@Before
	public void initializeMockRequestSender() {
		this.mockRequestSender.setWebServiceTester(this);
	}

}
