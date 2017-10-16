package com.dereekb.gae.test.applications.core.model;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.spring.CoreApiServiceTestingContext;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;

/**
 * Abstract ModelQueryTest for TallyNote.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <Q>
 *            query type
 */
public abstract class AbstractModelQueryTest<T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters> extends CoreApiServiceTestingContext {

	protected final Class<Q> queryClass;

	protected TestModelGenerator<T> modelGenerator;
	protected ObjectifyRegistry<T> modelRegistry;

	public AbstractModelQueryTest(Class<Q> queryClass) {
		this.queryClass = queryClass;
	}

	public Class<Q> getQueryClass() {
		return this.queryClass;
	}

	public TestModelGenerator<T> getModelGenerator() {
		return this.modelGenerator;
	}

	public void setModelGenerator(TestModelGenerator<T> modelGenerator) {
		if (modelGenerator == null) {
			throw new IllegalArgumentException("ModelGenerator cannot be null.");
		}

		this.modelGenerator = modelGenerator;
	}

	public ObjectifyRegistry<T> getModelRegistry() {
		return this.modelRegistry;
	}

	public void setModelRegistry(ObjectifyRegistry<T> modelRegistry) {
		if (modelRegistry == null) {
			throw new IllegalArgumentException("ModelRegistry cannot be null.");
		}

		this.modelRegistry = modelRegistry;
	}

	// MARK: Helper Functions
	protected List<T> queryModel(Q query) {
		return this.query(query, null).queryModels();
	}
	
	protected ExecutableObjectifyQuery<T> query(Q query) {
		return this.query(query, null);
	}
	
	protected ExecutableObjectifyQuery<T> query(Q query, ObjectifyQueryRequestOptions options) {
		ObjectifyQueryRequestBuilder<T> builder = this.queryBuilder(query, options);
		return builder.buildExecutableQuery();
	}

	protected ObjectifyQueryRequestBuilder<T> queryBuilder(Q query) {
		return this.queryBuilder(query, null);
	}

	protected ObjectifyQueryRequestBuilder<T> queryBuilder(Q query, ObjectifyQueryRequestOptions options) {
		Map<String, String> parameters = query.getParameters();
		return this.queryBuilder(parameters, options);
	}
	
	protected ObjectifyQueryRequestBuilder<T> queryBuilder(Map<String, String> parameters, ObjectifyQueryRequestOptions options) {
		ObjectifyQueryRequestBuilder<T> builder = this.modelRegistry.makeQuery(parameters);
		
		if (options != null) {
			builder.setOptions(options);
		}
		
		return builder;
	}

	protected Q makeQuery() {
		try {
			return this.queryClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	
}
