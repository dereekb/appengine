package com.dereekb.gae.test.applications.api.api.stored.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.test.applications.api.api.tests.ApiReadTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Deprecated
public class StoredImageApiReadTest extends ApiReadTest<StoredImage> {

	@Override
	@Autowired
	@Qualifier("storedImageType")
	public void setModelType(String modelType) {
		super.setModelType(modelType);
	}

	@Override
	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImage> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
