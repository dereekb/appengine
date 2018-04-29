package com.gae.server.service.login.test.application.api.login.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.test.applications.api.api.tests.client.ClientApiSearchTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginClientQueryTests extends ClientApiSearchTest<Login> {

	@Override
	@Autowired
	@Qualifier("loginTestModelGenerator")
	public void setTestModelGenerator(TestModelGenerator<Login> testLoginGenerator) {
		super.setTestModelGenerator(testLoginGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginClientQueryRequestSender")
	public void setQueryRequestSender(ClientQueryRequestSender<Login> requestSender) {
		super.setQueryRequestSender(requestSender);
	}

}
