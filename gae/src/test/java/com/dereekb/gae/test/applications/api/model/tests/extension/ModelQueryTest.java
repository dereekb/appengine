package com.dereekb.gae.test.applications.api.model.tests.extension;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public abstract class ModelQueryTest<T extends ObjectifyModel<T>> extends ApiApplicationTestContext {

	protected TestModelGenerator<T> generator;

	public TestModelGenerator<T> getGenerator() {
		return this.generator;
	}

	public void setGenerator(TestModelGenerator<T> generator) {
		this.generator = generator;
	}

}
