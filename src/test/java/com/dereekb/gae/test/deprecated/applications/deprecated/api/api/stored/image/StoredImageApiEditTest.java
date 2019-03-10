package com.dereekb.gae.test.applications.api.api.stored.image;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.dto.StoredImageData;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.test.deprecated.applications.api.api.tests.ApiEditTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;

@Disabled
@Deprecated
public class StoredImageApiEditTest extends ApiEditTest<StoredImage, StoredImageData> {

	@Override
	@Autowired
	@Qualifier("storedImageRegistry")
	public void setGetter(Getter<StoredImage> getter) {
		super.setGetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("storedImageEditController")
	public void setController(EditModelController<StoredImage, StoredImageData> controller) {
		super.setController(controller);
	}

	@Override
	@Autowired
	@Qualifier("storedImageDataBuilder")
	public void setConverter(DirectionalConverter<StoredImage, StoredImageData> converter) {
		super.setConverter(converter);
	}

	@Override
	@Autowired
	@Qualifier("storedImageTestDataGenerator")
	public void setModelDataGenerator(Generator<StoredImageData> modelDataGenerator) {
		super.setModelDataGenerator(modelDataGenerator);
	}

	@Override
	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImage> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
