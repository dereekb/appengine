package com.dereekb.gae.test.applications.api.model.tests.extension;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public abstract class ModelSearchImplementationTester<T extends UniqueModel> extends ApiApplicationTestContext {

	private Integer genCount = 5;

	protected ReadService<T> readService;
	protected GetterSetter<T> getterSetter;
	protected TestModelGenerator<T> modelGenerator;

	public ModelSearchImplementationTester(ReadService<T> readService,
	        GetterSetter<T> getterSetter,
	        TestModelGenerator<T> modelGenerator) {
		this.readService = readService;
		this.getterSetter = getterSetter;
		this.modelGenerator = modelGenerator;
	}

}
