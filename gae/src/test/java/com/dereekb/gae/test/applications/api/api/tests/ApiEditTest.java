package com.dereekb.gae.test.applications.api.api.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.request.ApiCreateRequest;
import com.dereekb.gae.web.api.model.crud.request.ApiDeleteRequest;
import com.dereekb.gae.web.api.model.crud.request.ApiUpdateRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * Tests Creating, Updating, and Deleting models through the API.
 *
 * @author dereekb
 *
 */
public abstract class ApiEditTest<T extends UniqueModel, I extends UniqueModel> extends ApiApplicationTestContext {

	protected Integer genCount = 5;

	protected Getter<T> getter;
	protected Generator<I> modelDataGenerator;
	protected DirectionalConverter<T, I> converter;
	protected EditModelController<T, I> controller;
	protected TestModelGenerator<T> modelGenerator;

	public Integer getGenCount() {
		return this.genCount;
	}

	public void setGenCount(Integer genCount) {
		this.genCount = genCount;
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		this.getter = getter;
	}

	public Generator<I> getModelDataGenerator() {
		return this.modelDataGenerator;
	}

	public void setModelDataGenerator(Generator<I> modelDataGenerator) {
		this.modelDataGenerator = modelDataGenerator;
	}

	public DirectionalConverter<T, I> getConverter() {
		return this.converter;
	}

	public void setConverter(DirectionalConverter<T, I> converter) {
		this.converter = converter;
	}

	public EditModelController<T, ?> getController() {
		return this.controller;
	}

	public void setController(EditModelController<T, I> controller) {
		this.controller = controller;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	// Tests
	@Test
	public void testCreateFunction() {
		List<I> modelData = this.modelDataGenerator.generate(5, null);

		ApiCreateRequest<I> request = new ApiCreateRequest<I>();
		request.setData(modelData);

		ApiResponse response = this.controller.create(request);

		ApiResponseData responseData = response.getResponsePrimaryData();
		Assert.assertNotNull(responseData);

		Object data = responseData.getResponseData();
		Assert.assertNotNull(data);

		@SuppressWarnings("unchecked")
		List<I> resultData = (List<I>) data;

		Assert.assertTrue(resultData.size() == modelData.size());
	}

	@Test
	public void testEditFunction() {
		List<T> models = this.modelGenerator.generate(5);
		List<I> modelData = this.converter.convert(models);

		ApiUpdateRequest<I> request = new ApiUpdateRequest<I>();
		request.setData(modelData);

		ApiResponse response = this.controller.update(request);
		ApiResponseData responseData = response.getResponsePrimaryData();
		Assert.assertNotNull(responseData);

		Object data = responseData.getResponseData();
		Assert.assertNotNull(data);

		@SuppressWarnings("unchecked")
		List<I> resultData = (List<I>) data;
		Assert.assertTrue(resultData.size() == modelData.size());
	}

	@Test
	public void testEditWithNoIdentifier() {
		T model = this.modelGenerator.generateModelWithoutKey();
		List<I> modelDatas = this.converter.convert(SingleItem.withValue(model));

		ApiUpdateRequest<I> request = new ApiUpdateRequest<I>();
		request.setData(modelDatas);

		try {
			this.controller.update(request);
			Assert.fail("Should fail due to no identifier on input element.");
		} catch (Exception e) {

		}
	}

	@Test
	public void testEditWithMultipleOfSameObject() {
		T model = this.modelGenerator.generate();
		List<T> models = new ArrayList<T>();

		models.add(model);
		models.add(model);

		List<I> modelDatas = this.converter.convert(models);

		ApiUpdateRequest<I> request = new ApiUpdateRequest<I>();
		request.setData(modelDatas);

		ApiResponse response = this.controller.update(request);
		Assert.assertTrue(response.getResponseSuccess());
	}

	@Test
	public void testDeleteFunction() {
		List<T> models = this.modelGenerator.generate(5);
		List<String> stringIdentifiers = ModelKey.readStringKeys(models);

		ApiDeleteRequest request = new ApiDeleteRequest();
		request.setData(stringIdentifiers);

		ApiResponse response = this.controller.delete(request);
		ApiResponseData responseData = response.getResponsePrimaryData();
		Assert.assertNotNull(responseData);

		Object data = responseData.getResponseData();
		Assert.assertNotNull(data);

		/*
		List<ModelKey> modelKeys = ModelKey.readModelKeys(models);
		@SuppressWarnings("unchecked")
		List<I> resultData = (List<I>) data;

		Assert.assertTrue(resultData.size() == models.size());
		List<T> reread = this.getter.getWithKeys(modelKeys);

		Assert.assertTrue(reread.isEmpty());
		*/
	}

}
