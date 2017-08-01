package com.dereekb.gae.test.applications.api.model.login.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.test.applications.api.model.AbstractServerModelRequestSenderTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


public class LoginPointerServerUserApiTest extends AbstractServerModelRequestSenderTest<LoginPointer> {

	@Override
	@Autowired
	@Qualifier("loginPointerTestModelGenerator")
	public void setTestModelGenerator(TestModelGenerator<LoginPointer> testLoginPointerGenerator) {
		super.setTestModelGenerator(testLoginPointerGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerClientCreateRequestSender")
	public void setCreateRequestSender(ClientCreateRequestSender<LoginPointer> requestSender) {
		super.setCreateRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerClientReadRequestSender")
	public void setReadRequestSender(ClientReadRequestSender<LoginPointer> requestSender) {
		super.setReadRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerClientUpdateRequestSender")
	public void setUpdateRequestSender(ClientUpdateRequestSender<LoginPointer> requestSender) {
		super.setUpdateRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerClientDeleteRequestSender")
	public void setDeleteRequestSender(ClientDeleteRequestSender<LoginPointer> requestSender) {
		super.setDeleteRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerClientQueryRequestSender")
	public void setQueryRequestSender(ClientQueryRequestSender<LoginPointer> requestSender) {
		super.setQueryRequestSender(requestSender);
	}

	public LoginPointerServerUserApiTest() {
		this.setCanCreateModel(false);
	}
}
