package com.dereekb.gae.test.applications.api.model.stored.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.model.tests.crud.core.CrudServiceTester;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class StoredImageCrudTest extends CrudServiceTester<StoredImage> {

	@Override
	@Autowired
	@Qualifier("storedImageCrudService")
	public void setService(CrudService<StoredImage> service) {
		super.setService(service);
	}

	@Override
	@Autowired
	@Qualifier("storedImageRegistry")
	public void setGetterSetter(GetterSetter<StoredImage> getterSetter) {
		super.setGetterSetter(getterSetter);
	}

	@Override
	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImage> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
