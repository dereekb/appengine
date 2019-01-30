package com.dereekb.gae.test.applications.api.api.stored.blob;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.test.applications.api.api.tests.ApiReadTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Ignore
@Deprecated
public class StoredBlobApiReadTest extends ApiReadTest<StoredBlob> {

	@Override
	@Autowired
	@Qualifier("storedBlobType")
	public void setModelType(String modelType) {
		super.setModelType(modelType);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredBlob> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
