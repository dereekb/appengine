package com.dereekb.gae.test.applications.api.api.tests;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.api.tests.client.ClientApiCrudTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.crud.controller.ReadController;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * Tests reading models through the API.
 *
 * @author dereekb
 * 
 * @deprecated Use {@link ClientApiCrudTest} instead.
 */
@Deprecated
public abstract class ApiReadTest<T extends UniqueModel> extends ApiApplicationTestContext {

	private Integer genCount = 5;

	@Autowired
	@Qualifier("readController")
	private ReadController controller;

	private String modelType;
	private TestModelGenerator<T> modelGenerator;

	public Integer getGenCount() {
		return this.genCount;
	}

	public void setGenCount(Integer genCount) {
		this.genCount = genCount;
	}

	public ReadController getController() {
		return this.controller;
	}

	public void setController(ReadController controller) {
		this.controller = controller;
	}

	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	// MARK: Tests
	@Test
	public void testSuccessfulReadRequest() {
		List<T> models = this.modelGenerator.generate(this.genCount);
		List<String> stringKeys = ModelKey.readStringKeys(models);

		try {
			ApiResponse response = this.controller.readModels(this.modelType, stringKeys, true, false, null);

			ApiResponseData responseData = response.getResponsePrimaryData();
			assertNotNull(responseData);

			Object data = responseData.getResponseData();
			assertNotNull(data);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAtomicReadRequestFailure() {
		List<String> stringKeys = new ArrayList<String>();

		stringKeys.add("10000");
		stringKeys.add("20000");
		stringKeys.add("30000");

		try {
			this.controller.readModels(this.modelType, stringKeys, true, false, null);
			fail();
		} catch (MissingRequiredResourceException e) {
			List<String> resources = e.getResources();
			assertNotNull(resources);
			assertTrue(resources.size() == stringKeys.size());
			assertTrue(resources.containsAll(stringKeys));
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
			ApiResponse response = this.controller.readModels(this.modelType, stringKeys, false, false, null);

			List<ApiResponseError> errors = response.getResponseErrors();
			assertNotNull(errors);
			assertFalse(errors.isEmpty());

			// TODO: Check response further to make sure they match.
		} catch (MissingRequiredResourceException e) {
			fail();
		}
	}

	@Test
	public void testSuccessfulReadRequestWithRelated() {
		List<T> models = this.modelGenerator.generate(this.genCount);
		List<String> stringKeys = ModelKey.readStringKeys(models);

		try {
			ApiResponse response = this.controller.readModels(this.modelType, stringKeys, true, true, null);

			ApiResponseData responseData = response.getResponsePrimaryData();
			assertNotNull(responseData);

			Object data = responseData.getResponseData();
			assertNotNull(data);
		} catch (Exception e) {
			fail();
		}
	}

}
