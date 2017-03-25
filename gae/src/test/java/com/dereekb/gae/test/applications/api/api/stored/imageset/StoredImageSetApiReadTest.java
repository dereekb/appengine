package com.dereekb.gae.test.applications.api.api.stored.imageset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.test.applications.api.api.tests.ApiReadTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Deprecated
public class StoredImageSetApiReadTest extends ApiReadTest<StoredImageSet> {

	@Override
	@Autowired
	@Qualifier("storedImageSetType")
	public void setModelType(String modelType) {
		super.setModelType(modelType);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImageSet> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
