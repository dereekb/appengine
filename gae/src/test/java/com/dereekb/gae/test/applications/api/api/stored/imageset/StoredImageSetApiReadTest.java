package com.dereekb.gae.test.applications.api.api.stored.imageset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.test.applications.api.api.tests.ApiReadTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.controller.ReadModelController;

public class StoredImageSetApiReadTest extends ApiReadTest<StoredImageSet> {

	@Override
	@Autowired
	@Qualifier("storedImageSetReadController")
	public void setController(ReadModelController<StoredImageSet> controller) {
		super.setController(controller);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImageSet> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
