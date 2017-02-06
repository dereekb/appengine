package com.dereekb.gae.test.applications.api.model.login.key;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.model.tests.crud.core.CrudServiceTester;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginKeyCrudTest extends CrudServiceTester<LoginKey> {

	@Override
	@Autowired
	@Qualifier("loginKeyCrudService")
	public void setService(CrudService<LoginKey> service) {
		super.setService(service);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyRegistry")
	public void setGetterSetter(GetterSetter<LoginKey> getterSetter) {
		super.setGetterSetter(getterSetter);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<LoginKey> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Ignore
	@Test
	public void testCreateService() {
		// Ignored, since login keys are created special and require an API
		// context.
	}

}
