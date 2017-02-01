package com.dereekb.gae.test.applications.api.model.login.login;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.model.tests.crud.core.CrudServiceTester;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginCrudTest extends CrudServiceTester<Login> {

	@Override
	@Autowired
	@Qualifier("loginCrudService")
	public void setService(CrudService<Login> service) {
		super.setService(service);
	}

	@Override
	@Autowired
	@Qualifier("loginRegistry")
	public void setGetterSetter(GetterSetter<Login> getterSetter) {
		super.setGetterSetter(getterSetter);
	}

	@Override
    @Autowired
	@Qualifier("loginTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<Login> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Ignore
	@Test
	public void testCreateService() {}

}
