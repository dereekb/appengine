package com.dereekb.gae.test.applications.api.model.tests.crud;

import org.junit.Test;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


public abstract class CrudServiceTester<T extends UniqueModel> extends ApiApplicationTestContext {

	private CrudService<T> service;
	private GetterSetter<T> getterSetter;
	private TestModelGenerator<T> modelGenerator;

	public CrudServiceTester() {}

	public CrudServiceTester(CrudService<T> service, GetterSetter<T> getterSetter, TestModelGenerator<T> modelGenerator) {
		this.service = service;
		this.getterSetter = getterSetter;
		this.modelGenerator = modelGenerator;
	}

	public CrudService<T> getService() {
		return this.service;
	}

	public void setService(CrudService<T> service) {
		this.service = service;
	}

	public GetterSetter<T> getGetterSetter() {
		return this.getterSetter;
	}

	public void setGetterSetter(GetterSetter<T> getterSetter) {
		this.getterSetter = getterSetter;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	@Test
	public void testCreateService() {
		CreateServiceTester<T> test = new CreateServiceTester<T>(this.service, this.getterSetter, this.modelGenerator);
		test.runTests();
	}

	@Test
	public void testReadService() {
		ReadServiceTester<T> test = new ReadServiceTester<T>(this.service, this.getterSetter, this.modelGenerator);
		test.runTests();
	}

	@Test
	public void testUpdateService() {
		UpdateServiceTester<T> test = new UpdateServiceTester<T>(this.service, this.getterSetter, this.modelGenerator);
		test.runTests();
	}

	@Test
	public void testDeleteService() {
		DeleteServiceTester<T> test = new DeleteServiceTester<T>(this.service, this.getterSetter, this.modelGenerator);
		test.runTests();
	}

}
