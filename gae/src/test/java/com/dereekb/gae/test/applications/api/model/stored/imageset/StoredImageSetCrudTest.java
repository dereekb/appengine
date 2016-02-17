package com.dereekb.gae.test.applications.api.model.stored.imageset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.model.tests.crud.CrudServiceTester;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class StoredImageSetCrudTest extends CrudServiceTester<StoredImageSet> {

	@Override
	@Autowired
	@Qualifier("storedImageSetCrudService")
	public void setService(CrudService<StoredImageSet> service) {
		super.setService(service);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetRegistry")
	public void setGetterSetter(GetterSetter<StoredImageSet> getterSetter) {
		super.setGetterSetter(getterSetter);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImageSet> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
