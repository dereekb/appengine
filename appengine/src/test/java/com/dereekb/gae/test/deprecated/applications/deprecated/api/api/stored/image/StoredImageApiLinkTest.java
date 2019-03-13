package com.dereekb.gae.test.applications.api.api.stored.image;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.deprecated.applications.api.api.tests.ApiLinkTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Disabled
@Deprecated
public class StoredImageApiLinkTest extends ApiLinkTest<StoredImage> {

	@Override
	@Autowired
	@Qualifier("storedImageType")
	public void setModelType(String modelType) {
		super.setModelType(modelType);
	}

	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	private TestModelGenerator<StoredImage> storedImageGenerator;

	@Autowired
	@Qualifier("storedImageRegistry")
	private GetterSetter<StoredImage> storedImageGetterSetter;

}
