package com.dereekb.gae.test.applications.api.model.stored.blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.model.tests.crud.CrudServiceTester;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class StoredBlobCrudTest extends CrudServiceTester<StoredBlob> {

	@Override
	@Autowired
	@Qualifier("storedBlobCrudService")
	public void setService(CrudService<StoredBlob> service) {
		super.setService(service);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobRegistry")
	public void setGetterSetter(GetterSetter<StoredBlob> getterSetter) {
		super.setGetterSetter(getterSetter);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredBlob> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
