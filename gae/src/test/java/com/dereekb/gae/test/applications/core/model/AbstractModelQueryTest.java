package com.dereekb.gae.test.applications.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.test.applications.core.CoreApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

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
public abstract class AbstractModelQueryTest<T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters> extends CoreApplicationTestContext {

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
	protected List<T> queryModels(Q query) {
		return this.query(query, null).queryModels();
	}

	protected ExecutableObjectifyQuery<T> query(Q query) {
		return this.query(query, null);
	}

	protected ExecutableObjectifyQuery<T> query(Q query,
	                                            ObjectifyQueryRequestOptions options) {
		ObjectifyQueryRequestBuilder<T> builder = this.queryBuilder(query, options);
		return builder.buildExecutableQuery();
	}

	protected ObjectifyQueryRequestBuilder<T> queryBuilder(Q query) {
		return this.queryBuilder(query, null);
	}

	protected ObjectifyQueryRequestBuilder<T> queryBuilder(Q query,
	                                                       ObjectifyQueryRequestOptions options) {
		Map<String, String> parameters = query.getParameters();
		return this.queryBuilder(parameters, options);
	}

	protected ObjectifyQueryRequestBuilder<T> queryBuilder(Map<String, String> parameters,
	                                                       ObjectifyQueryRequestOptions options) {
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

	// MARK: Testers
	public static abstract class AbstractDateQueryTester<T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters> extends DateQueryTester<T, Q>
	        implements DateQueryTesterDelegate<T, Q> {

		public AbstractDateQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
			this.setDelegate(this);
		}

		// MARK: Delegate
		@Override
		public Date makeTestValue(int key) {
			return new Date(1000 * key);
		}

	}

	public static interface DateQueryTesterDelegate<T, Q>
	        extends AbstractValueQueryTesterDelegate<Date, T, Q> {

		public void configureQueryForValueTest(Q query,
		                                       QueryResultsOrdering ordering);

		public Comparator<T> getAscendingValueComparator();

	}

	/**
	 * Date Query Tester.
	 *
	 * @author dereekb
	 *
	 * @param <T>
	 *            model type
	 * @param <Q>
	 *            query type
	 */
	public static class DateQueryTester<T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters> extends AbstractValueQueryTester<Date, T, Q, DateQueryTesterDelegate<T, Q>> {

		protected DateQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
		}

		public DateQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest, DateQueryTesterDelegate<T, Q> delegate) {
			super(modelQueryTest, delegate);
		}

		// MARK: Tests
		public void testQueryingForNewestByDate() {
			this.testQueryingForModelsByDate(QueryResultsOrdering.Descending);
		}

		public void testQueryingForOldestByDate() {
			this.testQueryingForModelsByDate(QueryResultsOrdering.Ascending);
		}

		public void testQueryingForModelsByDate(QueryResultsOrdering ordering) {
			DateQueryTesterDelegate<T, Q> delegate = this.getDelegate();

			int count = 10;
			List<T> models = this.modelQueryTest.getModelGenerator().generate(count);

			for (int i = 0; i < models.size(); i += 1) {
				T model = models.get(i);

				Date date = new Date(i * 20000);
				delegate.setValueForTest(model, date);
			}

			this.modelQueryTest.getModelRegistry().update(models);

			// Always sort ascending
			Collections.sort(models, delegate.getAscendingValueComparator());

			// Reverse for descending
			if (ordering == QueryResultsOrdering.Descending) {
				Collections.reverse(models);
			}

			Q query = this.modelQueryTest.makeQuery();
			delegate.configureQueryForValueTest(query, ordering);

			ObjectifyQueryRequestBuilder<T> builder = this.modelQueryTest.queryBuilder(query);

			List<T> results = builder.buildExecutableQuery().queryModels();
			Assert.assertTrue(results.size() == models.size());
			Assert.assertTrue(results.containsAll(models));

			Assert.assertTrue(ListUtility.checkExactOrder(models, results));
		}

		public void testQueryingForModelsOnAndAfterDate() {
			this.testQueryingForValueByValue(ExpressionOperator.GREATER_OR_EQUAL_TO);
		}

		public void testQueryingForModelsBeforeDate() {
			this.testQueryingForValueByValue(ExpressionOperator.LESS_THAN);
		}

	}

	// MARK: Integer
	public static abstract class AbstractIntegerQueryTester<T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters> extends IntegerQueryTester<T, Q>
	        implements IntegerQueryTesterDelegate<T, Q> {

		public AbstractIntegerQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
			this.setDelegate(this);
		}

		// MARK: Delegate
		@Override
		public Integer makeTestValue(int key) {
			return key;
		}

	}

	public static interface IntegerQueryTesterDelegate<T, Q>
	        extends AbstractValueQueryTesterDelegate<Integer, T, Q> {}

	public static class IntegerQueryTester<T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters> extends AbstractValueQueryTester<Integer, T, Q, IntegerQueryTesterDelegate<T, Q>> {

		protected IntegerQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
		}

		public IntegerQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest,
		        IntegerQueryTesterDelegate<T, Q> delegate) {
			super(modelQueryTest, delegate);
		}

	}

	// MARK: Boolean
	public static abstract class AbstractBooleanQueryTester<T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters> extends BooleanQueryTester<T, Q>
	        implements BooleanQueryTesterDelegate<T, Q> {

		public AbstractBooleanQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
			this.setDelegate(this);
		}

		// MARK: Delegate
		@Override
		public Boolean makeTestValue(int key) {
			return ((key % 1) == 0) ? false : true;
		}

	}

	public static interface BooleanQueryTesterDelegate<T, Q>
	        extends AbstractValueQueryTesterDelegate<Boolean, T, Q> {}

	public static class BooleanQueryTester<T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters> extends AbstractValueQueryTester<Boolean, T, Q, BooleanQueryTesterDelegate<T, Q>> {

		protected BooleanQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
		}

		public BooleanQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest,
		        BooleanQueryTesterDelegate<T, Q> delegate) {
			super(modelQueryTest, delegate);
		}

	}

	// MARK: Model Key
	public static abstract class AbstractModelKeyQueryTester<T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters> extends ModelKeyQueryTester<T, Q>
	        implements ModelKeyQueryTesterDelegate<T, Q> {

		private ModelKeyType type = ModelKeyType.NUMBER;

		public AbstractModelKeyQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
			this.setDelegate(this);
		}

		// MARK: Delegate
		@Override
		public ModelKey makeTestValue(int key) {
			switch (this.type) {
				case NAME:
					return new ModelKey(String.valueOf(key));
				case NUMBER:
					return new ModelKey(key);
				default:
					throw new RuntimeException("Invalid key type.");
			}
		}

	}

	public static interface ModelKeyQueryTesterDelegate<T, Q>
	        extends AbstractValueQueryTesterDelegate<ModelKey, T, Q> {}

	public static class ModelKeyQueryTester<T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters> extends AbstractValueQueryTester<ModelKey, T, Q, ModelKeyQueryTesterDelegate<T, Q>> {

		public ModelKeyQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest,
		        ModelKeyQueryTesterDelegate<T, Q> delegate) {
			super(modelQueryTest, delegate);
		}

		public ModelKeyQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
		}

	}

	// MARK: Abstract Value
	public static interface AbstractValueQueryTesterDelegate<V, T, Q>
	        extends AbstractQueryTesterDelegate<V, T, Q> {

		/**
		 * Creates a new test value from the input int.
		 * <p>
		 * Values should increase linearly, so those with a key greater than the
		 * previous should have a greater value.
		 *
		 * @param key
		 * @return
		 */
		public V makeTestValue(int key);

	}

	public static abstract class AbstractValueQueryTester<V, T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters, D extends AbstractValueQueryTesterDelegate<V, T, Q>> extends AbstractQueryTester<V, T, Q, D> {

		public AbstractValueQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest, D delegate) {
			super(modelQueryTest, delegate);
		}

		public AbstractValueQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
		}

		// MARK: Tests
		protected V generateValueForValueExactTest() {
			Integer key = 2;
			return this.getDelegate().makeTestValue(key);
		}

		protected V generateExtraValueForValueExactTest() {
			Integer extraValueKey = 1;
			return this.getDelegate().makeTestValue(extraValueKey);
		}

		public void testQueryingForValueExact() {
			D delegate = this.getDelegate();

			int count = 3;
			List<T> models = this.modelQueryTest.getModelGenerator().generate(count);

			V value = this.generateValueForValueExactTest();

			for (T model : models) {
				delegate.setValueForTest(model, value);
			}

			// Update Models
			this.modelQueryTest.getModelRegistry().update(models);

			// Generate More Models
			List<T> extraModels = this.modelQueryTest.getModelGenerator().generate(count);

			V extraValue = this.generateExtraValueForValueExactTest();

			for (T model : extraModels) {
				delegate.setValueForTest(model, extraValue);
			}

			this.modelQueryTest.getModelRegistry().update(extraModels);

			// Always Sort Ascending
			Q query = this.modelQueryTest.makeQuery();
			delegate.configureQueryForValueExpressionTest(query, value, ExpressionOperator.EQUAL);

			List<T> results = this.modelQueryTest.queryModels(query);
			Assert.assertTrue(results.size() == count);
			Assert.assertTrue(results.containsAll(models));
		}

		public void testQueryingForValueGreaterThanOrEqual() {
			this.testQueryingForValueByValue(ExpressionOperator.GREATER_OR_EQUAL_TO);
		}

		public void testQueryingForValueLessThanOrEqual() {
			this.testQueryingForValueByValue(ExpressionOperator.LESS_OR_EQUAL_TO);
		}

		public void testQueryingForValueByValue(ExpressionOperator expression) {
			D delegate = this.getDelegate();

			int count = 10;
			List<T> models = this.modelQueryTest.getModelGenerator().generate(count);

			List<T> beforeHalf = new ArrayList<T>();
			List<T> afterHalf = new ArrayList<T>();

			int half = count / 2;
			V halfValue = delegate.makeTestValue(half);

			for (int i = 0; i < count; i += 1) {
				T model = models.get(i);

				V value = delegate.makeTestValue(i);
				delegate.setValueForTest(model, value);

				if (i < half) {
					beforeHalf.add(model);
				} else {
					afterHalf.add(model);
				}
			}

			// Update Models
			this.modelQueryTest.getModelRegistry().update(models);

			// Always Sort Ascending
			Q query = this.modelQueryTest.makeQuery();
			delegate.configureQueryForValueExpressionTest(query, halfValue, expression);

			int expectedSize = half;

			switch (expression) {
				case EQUAL:
					expectedSize = 1;
					break;
				case GREATER_OR_EQUAL_TO:
					expectedSize = half;
					break;
				case GREATER_OR_LESS_BUT_NOT_EQUAL_TO:
					expectedSize = count - 1;
					break;
				case LESS_OR_EQUAL_TO:
					expectedSize = half + 1;
					break;
				case LESS_THAN:
					expectedSize = half;
					break;
				case GREATER_THAN:
					expectedSize = half - 1;
					break;
				default:
					throw new UnsupportedOperationException();
			}

			List<T> results = this.modelQueryTest.queryModels(query);
			Assert.assertTrue(results.size() == expectedSize);

			switch (expression) {
				case EQUAL:
					Assert.assertTrue(results.contains(models.get(half)));
					break;
				case GREATER_OR_EQUAL_TO:
					Assert.assertTrue(results.containsAll(afterHalf));
					break;
				case LESS_THAN:
					Assert.assertTrue(results.containsAll(beforeHalf));
					break;
				default:
					break;
			}
		}

	}

	// MARK: Abstract
	// TODO: Add separate delegate/extension that contains sorting/ordering
	// tests.

	public static interface AbstractQueryTesterDelegate<V, T, Q> {

		public void setValueForTest(T model,
		                            V value);

		public void configureQueryForValueExpressionTest(Q query,
		                                                 V value,
		                                                 ExpressionOperator operator);

	}

	public static abstract class AbstractQueryTester<V, T extends ObjectifyModel<T>, Q extends ConfigurableEncodedQueryParameters, D extends AbstractQueryTesterDelegate<V, T, Q>> {

		protected final AbstractModelQueryTest<T, Q> modelQueryTest;
		private D delegate;

		protected AbstractQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super();
			if (modelQueryTest == null) {
				throw new IllegalArgumentException("modelQueryTest cannot be null.");
			}

			this.modelQueryTest = modelQueryTest;
		}

		public AbstractQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest, D delegate) {
			this(modelQueryTest);
			this.setDelegate(delegate);
		}

		public AbstractModelQueryTest<T, Q> getModelQueryTest() {
			return this.modelQueryTest;
		}

		public void setModelQueryTest(AbstractModelQueryTest<T, Q> modelQueryTest) {}

		public D getDelegate() {
			return this.delegate;
		}

		public void setDelegate(D delegate) {
			if (delegate == null) {
				throw new IllegalArgumentException("delegate cannot be null.");
			}

			this.delegate = delegate;
		}

	}

}
