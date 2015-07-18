package com.dereekb.gae.test.applications.api.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.controller.ReadModelController;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * Tests reading models through the API.
 *
 * @author dereekb
 */
public abstract class ApiReadTest<T extends UniqueModel> extends ApiApplicationTestContext {

	private Integer genCount = 5;

	private ReadModelController<T> controller;
	private TestModelGenerator<T> modelGenerator;

	public ReadModelController<T> getController() {
		return this.controller;
	}

	public void setController(ReadModelController<T> controller) {
		this.controller = controller;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	@Test
	public void testSuccessfulReadRequest() {
		List<T> models = this.modelGenerator.generate(this.genCount);
		List<String> stringKeys = ModelKey.readStringKeys(models);

		try {
			ApiResponse response = this.controller.read(stringKeys, true, false);

			ApiResponseData responseData = response.getData();
			Assert.assertNotNull(responseData);

			Object data = responseData.getData();
			Assert.assertNotNull(data);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testAtomicReadRequestFailure() {
		List<String> stringKeys = new ArrayList<String>();

		stringKeys.add("10000");
		stringKeys.add("20000");
		stringKeys.add("30000");

		try {
			this.controller.read(stringKeys, true, false);
			Assert.fail();
		} catch (MissingRequiredResourceException e) {
			List<String> resources = e.getResources();
			Assert.assertNotNull(resources);
			Assert.assertTrue(resources.size() == stringKeys.size());
			Assert.assertTrue(resources.containsAll(stringKeys));
		}
	}

	@Test
	public void testPartialReadRequestFailure() {
		List<T> models = this.modelGenerator.generate(this.genCount);
		List<String> stringKeys = ModelKey.readStringKeys(models);
		List<String> fakeStringKeys = new ArrayList<String>();

		fakeStringKeys.add("10000");
		fakeStringKeys.add("20000");
		fakeStringKeys.add("30000");

		stringKeys.addAll(fakeStringKeys);

		try {
			ApiResponse response = this.controller.read(stringKeys, false, false);

			List<ApiResponseError> errors = response.getErrors();
			Assert.assertNotNull(errors);
			Assert.assertFalse(errors.isEmpty());

			// TODO: Check response further to make sure they match.
		} catch (MissingRequiredResourceException e) {
			Assert.fail();
		}
	}

}
