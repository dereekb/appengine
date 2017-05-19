package com.dereekb.gae.test.applications.api.api.login.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceRequestSender;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.test.applications.api.api.tests.client.ClientApiLinkTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginClientLinkTests extends ClientApiLinkTest<Login> {

	@Override
	@Autowired
	@Qualifier("loginTestModelGenerator")
	public void setTestModelGenerator(TestModelGenerator<Login> testLoginGenerator) {
		super.setTestModelGenerator(testLoginGenerator);
	}

	@Override
	@Autowired
	@Qualifier("clientLinkRequestSender")
	public void setLinkRequestSender(ClientLinkServiceRequestSender linkRequestSender) {
		super.setLinkRequestSender(linkRequestSender);
	}

}
