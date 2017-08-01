package com.dereekb.gae.test.applications.api.model.login.system;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.test.applications.api.model.AbstractServerModelRequestSenderTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


public class LoginKeyServerUserApiTest extends AbstractServerModelRequestSenderTest<LoginKey> {

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

	@Override
	@Autowired
	@Qualifier("loginKeyClientQueryRequestSender")
	public void setQueryRequestSender(ClientQueryRequestSender<LoginKey> requestSender) {
		super.setQueryRequestSender(requestSender);
	}

	public LoginKeyServerUserApiTest() {
		this.setCanCreateModel(false);
	}

	@Override
	@Ignore
	@Test
	public void testSystemClientCreateIsUnavailable() throws Exception {
		// LoginKeys cannot be created, but aren't restricted to the URI either.
	}

}
