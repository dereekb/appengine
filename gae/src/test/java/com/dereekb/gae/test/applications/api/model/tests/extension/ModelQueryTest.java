package com.dereekb.gae.test.applications.api.model.tests.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.utility.mock.MockHttpServletRequestBuilderUtility;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public abstract class ModelQueryTest<T extends ObjectifyModel<T>> extends ApiApplicationTestContext {

	private JsonParser parser = new JsonParser();

	@Autowired
	private TypeModelKeyConverter keyTypeConverter;

	private String queryType;

	private TestModelGenerator<T> modelGenerator;
	private ObjectifyRegistry<T> registry;

	public String getQueryType() {
		return this.queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

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
	 * Tests that the API results match the query results.
	 */
	@Test
	public void queryMatchingTest() throws Exception {
		Integer limit = 10;

		this.modelGenerator.generate(limit);

		ObjectifyQueryRequestOptionsImpl options = new ObjectifyQueryRequestOptionsImpl();
		options.setLimit(limit);

		this.testAndAssertQueryMatches(this.buildTestQueryConfigurer(), options);
	}

	protected ConfigurableObjectifyQueryRequestConfigurer buildTestQueryConfigurer() {
		return new ConfigurableObjectifyQueryRequestConfigurer() {

			@Override
			public Map<String, String> getParameters() {
				return Collections.emptyMap();
			}

			@Override
			public void setParameters(Map<String, String> parameters) throws IllegalArgumentException {}

			@Override
			public void configure(ObjectifyQueryRequestLimitedBuilder request) {}

		};
	}

	protected <Q extends ConfigurableObjectifyQueryRequestConfigurer> ModelQueryTest<T>.ModelQueryUnitTest<Q>.Results testAndAssertQueryMatches(Q query,
	                                                                                                                                            ObjectifyQueryRequestOptions options)
	        throws Exception {

		ModelQueryUnitTest<Q> test = new ModelQueryUnitTest<Q>(query, options);
		ModelQueryUnitTest<Q>.Results results = test.performQuery();

		results.assertResultsMatch();

		return results;
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
		private ObjectifyQueryRequestOptions options;

		public ModelQueryUnitTest(Q query) {
			super();
			this.query = query;
		}

		public ModelQueryUnitTest(Q query, ObjectifyQueryRequestOptions options) {
			super();
			this.query = query;
			this.options = options;
		}

		public Results performQuery() throws Exception {
			return this.performQuery(this.options);
		}

		public Results performQuery(ObjectifyQueryRequestOptions options) throws Exception {
			List<ModelKey> results = this.performModelHttpRequest(options);

			ObjectifyQueryRequestBuilder<T> builder = ModelQueryTest.this.registry.makeQuery();
			this.query.configure(builder);

			if (options != null) {
				builder.setOptions(options);
			}

			ExecutableObjectifyQuery<T> executableQuery = builder.buildExecutableQuery();

			return new Results(executableQuery, results);
		}

		public List<ModelKey> performModelHttpRequest(ObjectifyQueryRequestOptions options) throws Exception {
			MockHttpServletResponse response = this.performHttpRequest(options);

			Assert.assertTrue("Query failed.", response.getStatus() == 200);

			String jsonContent = response.getContentAsString();
			JsonElement jsonElement = ModelQueryTest.this.parser.parse(jsonContent);
			JsonElement jsonData = jsonElement.getAsJsonObject().get("data");
			List<String> stringResults = new ArrayList<String>();

			try {
				JsonArray keys = jsonData.getAsJsonObject().get("data").getAsJsonArray();

				for (JsonElement element : keys) {
					String keyString = element.getAsString();
					stringResults.add(keyString);
				}
			} catch (Exception e) {
			}

			return ModelQueryTest.this.keyTypeConverter.convertKeys(ModelQueryTest.this.queryType, stringResults);
		}

		public MockHttpServletResponse performHttpRequest(ObjectifyQueryRequestOptions options) throws Exception {
			MockHttpServletRequestBuilder request = this.getQueryRequestBuilder(options);

			request.param("keysOnly", "true");	// Return keys only.

			ResultActions result = ModelQueryTest.this.performHttpRequest(request);
			return result.andReturn().getResponse();
		}

		public MockHttpServletRequestBuilder getQueryRequestBuilder(ObjectifyQueryRequestOptions options) {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(this.getQueryUrl());

			MockHttpServletRequestBuilderUtility.addParameters(builder, this.query);

			if (options != null) {
				MockHttpServletRequestBuilderUtility.addParameters(builder, options);
			}

			return builder;
		}

		public String getQueryUrl() {
			if (ModelQueryTest.this.queryType == null) {
				throw new RuntimeException("Test query type cannot be null.");
			}

			return "/" + ModelQueryTest.this.queryType + "/query";
		}

		public class Results {

			private final ExecutableObjectifyQuery<T> query;
			private final List<ModelKey> modelKeys;

			public Results(ExecutableObjectifyQuery<T> query, List<ModelKey> modelKeys) {
				super();
				this.query = query;
				this.modelKeys = modelKeys;
			}

			public void assertResultsMatch() {
				List<ModelKey> queryKeyResults = this.query.queryModelKeys();
				Assert.assertTrue(queryKeyResults.size() == this.modelKeys.size());
				Assert.assertTrue(queryKeyResults.containsAll(this.modelKeys));
			}

			public ExecutableObjectifyQuery<T> getQuery() {
				return this.query;
			}

			public List<ModelKey> getModelKeys() {
				return this.modelKeys;
			}

		}

	}

}
