package com.dereekb.gae.test.applications.api.api.stored.imageset;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.stored.image.set.dto.StoredImageSetData;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.test.deprecated.applications.api.api.tests.ApiEditTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;

@Disabled
@Deprecated
public class StoredImageSetApiEditTest extends ApiEditTest<StoredImageSet, StoredImageSetData> {

	@Override
	@Autowired
	@Qualifier("storedImageSetRegistry")
	public void setGetter(Getter<StoredImageSet> getter) {
		super.setGetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetEditController")
	public void setController(EditModelController<StoredImageSet, StoredImageSetData> controller) {
		super.setController(controller);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetDataBuilder")
	public void setConverter(DirectionalConverter<StoredImageSet, StoredImageSetData> converter) {
		super.setConverter(converter);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetTestDataGenerator")
	public void setModelDataGenerator(Generator<StoredImageSetData> modelDataGenerator) {
		super.setModelDataGenerator(modelDataGenerator);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImageSet> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
