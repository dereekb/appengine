package com.dereekb.gae.test.applications.api.api.stored.blob;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.dto.StoredBlobData;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.api.tests.ApiEditTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.request.ApiDeleteRequest;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;

/**
 * 
 * @author dereekb
 * @deprecated Use Client api tests instead.
 */
@Deprecated
public class StoredBlobApiEditTest extends ApiEditTest<StoredBlob, StoredBlobData> {

	@Autowired
	@Qualifier("storedBlobRegistry")
	public ObjectifyRegistry<StoredBlob> storedBlobRegistry;

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

	@Test
	public void testDeleteRules() {
		StoredBlob storedBlob = this.modelGenerator.generate();
		String stringIdentifier = ModelKey.readStringKey(storedBlob.getModelKey());

		List<String> stringIdentifiers = new ArrayList<String>();
		stringIdentifiers.add(stringIdentifier);

		storedBlob.setDescriptorId("id");
		storedBlob.setDescriptorType("type");

		this.storedBlobRegistry.update(storedBlob);

		Assert.assertNotNull(storedBlob.getDescriptor());

		ApiDeleteRequest request = new ApiDeleteRequest(stringIdentifiers);

		try {
			this.controller.delete(request);
			Assert.fail();
		} catch (MissingRequiredResourceException e) {
			Assert.assertFalse(e.getResources().isEmpty());
			Assert.assertTrue(e.getResources().containsAll(stringIdentifiers));
		}

		storedBlob.setDescriptor(null);
		this.storedBlobRegistry.update(storedBlob);

		try {
			this.controller.delete(request);
		} catch (AtomicOperationException e) {
			Assert.fail();
		}
	}

}
