package com.dereekb.gae.test.applications.api.api.stored.blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.test.applications.api.api.ApiReadTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.controller.ReadModelController;

public class StoredBlobApiReadTest extends ApiReadTest<StoredBlob> {

	@Override
	@Autowired
	@Qualifier("storedBlobReadController")
	public void setController(ReadModelController<StoredBlob> controller) {
		super.setController(controller);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredBlob> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
