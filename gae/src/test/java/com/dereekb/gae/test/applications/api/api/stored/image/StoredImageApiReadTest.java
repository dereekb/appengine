package com.dereekb.gae.test.applications.api.api.stored.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.test.applications.api.api.ApiReadTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.controller.ReadModelController;

public class StoredImageApiReadTest extends ApiReadTest<StoredImage> {

	@Override
	@Autowired
	@Qualifier("storedImageReadController")
	public void setController(ReadModelController<StoredImage> controller) {
		super.setController(controller);
	}

	@Override
	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImage> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
