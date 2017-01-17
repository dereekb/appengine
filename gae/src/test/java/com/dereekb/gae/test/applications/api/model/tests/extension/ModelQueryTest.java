package com.dereekb.gae.test.applications.api.model.tests.extension;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.utility.mock.MockHttpServletRequestBuilderUtility;

public abstract class ModelQueryTest<T extends ObjectifyModel<T>> extends ApiApplicationTestContext {

	private String queryType;

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

		Assert.assertTrue(results.size() >= models.size());
	}

	/**
	 * Special unit test class that tests the query on both the API and the raw
	 * query.
	 * 
	 * @author dereekb
	 *
	 */
	public class ModelQueryUnitTest<Q extends ConfigurableObjectifyQueryRequestConfigurer> {

		private Q query;

		public ModelQueryUnitTestResults performQuery() {
			return this.performQuery(null);
		}

		public ModelQueryUnitTestResults performQuery(ObjectifyQueryRequestOptions options) {
			ObjectifyQueryRequestBuilder<T> results = ModelQueryTest.this.registry.makeQuery();

			return null;
		}

		public MockHttpServletRequestBuilder getQueryRequestBuilder(ObjectifyQueryRequestOptions options) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(this.getQueryUrl());

			MockHttpServletRequestBuilderUtility.addParameters(builder, this.query);

			if (options != null) {
				String cursor = options.getCursor();

			}

			return builder;
		}

		public String getQueryUrl() {
			return ModelQueryTest.this.queryType + "/query";
		}

		public class ModelQueryUnitTestResults {

		}

	}

}
