package com.dereekb.gae.test.applications.api.model.tests.extension;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public abstract class ModelQueryTest<T extends ObjectifyModel<T>> extends ApiApplicationTestContext {

	private TestModelGenerator<T> modelGenerator;
	private ObjectifyRegistry<T> registry;

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		this.modelGenerator = modelGenerator;
	}

	public ObjectifyRegistry<T> getRegistry() {
		return this.registry;
	}

	public void setRegistry(ObjectifyRegistry<T> registry) {
		this.registry = registry;
	}

	@Test
	public void testEmptyQuery() {
		List<T> models = this.modelGenerator.generate(5);

		ObjectifyQueryRequestBuilder<T> builder = this.registry.makeQuery();
		ExecutableObjectifyQuery<T> executable = builder.buildExecutableQuery();
		List<T> results = executable.queryModels();

		Assert.assertTrue("Models were returned from query.", models.containsAll(results));
	}

}
