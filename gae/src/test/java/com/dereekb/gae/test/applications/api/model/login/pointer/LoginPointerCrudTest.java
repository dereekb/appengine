package com.dereekb.gae.test.applications.api.model.login.pointer;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.model.tests.crud.core.CrudServiceTester;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginPointerCrudTest extends CrudServiceTester<LoginPointer> {

	@Override
	@Autowired
	@Qualifier("loginPointerCrudService")
	public void setService(CrudService<LoginPointer> service) {
		super.setService(service);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerRegistry")
	public void setGetterSetter(GetterSetter<LoginPointer> getterSetter) {
		super.setGetterSetter(getterSetter);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<LoginPointer> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Ignore
	@Test
	public void testCreateService() {
		// Ignored, since pointers are not created through the CRUD service.
	}

}
