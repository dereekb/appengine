package com.dereekb.gae.test.applications.core.model;

import java.util.Comparator;
import java.util.Date;

import org.junit.Test;

import com.dereekb.gae.model.extension.search.query.parameters.AbstractDateModelQuery;
import com.dereekb.gae.server.datastore.objectify.model.MutableDatedObjectifyModel;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;
import com.dereekb.gae.utilities.time.model.DatedModelAscendingComparator;

/**
 * Abstract ModelQueryTest for {@link AbstractDateModelQuery}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <Q>
 *            query type
 */
public abstract class AbstractDateModelQueryTest<T extends MutableDatedObjectifyModel<T>, Q extends AbstractDateModelQuery> extends AbstractModelQueryTest<T, Q> {

	public AbstractDateModelQueryTest(Class<Q> queryClass) {
		super(queryClass);
	}

	@Test
	public void testQueryingForNewestModelsByDate() {
		new DateQueryTester<T, Q>(this).testQueryingForNewestByDate();
	}

	@Test
	public void testQueryingForOldestModelsByDate() {
		new DateQueryTester<T, Q>(this).testQueryingForOldestByDate();
	}

	@Test
	public void testQueryingOnAndAfterDate() throws InstantiationException, IllegalAccessException {
		AbstractDateModelQueryTest.DateQueryTester.make(this).testQueryingForModelsOnAndAfterDate();
	}

	@Test
	public void testQueryingBeforeDate() throws InstantiationException, IllegalAccessException {
		AbstractDateModelQueryTest.DateQueryTester.make(this).testQueryingForModelsBeforeDate();
	}

	// MARK: Tester
	public static class DateQueryTester<T extends MutableDatedObjectifyModel<T>, Q extends AbstractDateModelQuery> extends AbstractDateQueryTester<T, Q>
	        implements AbstractDateQueryTesterDelegate<T, Q> {

		public static <T extends MutableDatedObjectifyModel<T>, Q extends AbstractDateModelQuery> DateQueryTester<T, Q> make(AbstractModelQueryTest<T, Q> modelQueryTest) {
			return new DateQueryTester<T, Q>(modelQueryTest);
		}

		public DateQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
			this.setDelegate(this);
		}

		// MARK: AbstractDateQueryTesterDelegate
		@Override
		public void setValueForTest(T model,
		                            Date value) {
			model.setDate(value);
		}

		@Override
		public void configureQueryForValueTest(Q query,
		                                       QueryResultsOrdering ordering) {
			query.orderByDates(ordering);
		}

		@Override
		public Comparator<T> getAscendingValueComparator() {
			return new DatedModelAscendingComparator<T>();
		}

		@Override
		public void configureQueryForValueExpressionTest(Q query,
		                                                 Date date,
		                                                 ExpressionOperator operator) {
			query.searchDates(date, operator);
		}

	}

}
