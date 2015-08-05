package com.dereekb.gae.test.applications.api.api.stored.blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.dto.StoredBlobData;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.test.applications.api.api.ApiEditTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.controller.EditModelController;

public class StoredBlobApiEditTest extends ApiEditTest<StoredBlob, StoredBlobData> {

	@Override
	@Autowired
	@Qualifier("storedBlobRegistry")
	public void setGetter(Getter<StoredBlob> getter) {
		super.setGetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobEditController")
	public void setController(EditModelController<StoredBlob, StoredBlobData> controller) {
		super.setController(controller);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobDataBuilder")
	public void setConverter(DirectionalConverter<StoredBlob, StoredBlobData> converter) {
		super.setConverter(converter);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobTestDataGenerator")
	public void setModelDataGenerator(Generator<StoredBlobData> modelDataGenerator) {
		super.setModelDataGenerator(modelDataGenerator);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredBlob> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
