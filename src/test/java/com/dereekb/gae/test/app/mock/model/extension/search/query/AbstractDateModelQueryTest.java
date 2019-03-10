package com.dereekb.gae.test.app.mock.model.extension.search.query;

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
		new DateModelQueryTester<T, Q>(this).testQueryingForNewestByDate();
	}

	@Test
	public void testQueryingForOldestModelsByDate() {
		new DateModelQueryTester<T, Q>(this).testQueryingForOldestByDate();
	}

	@Test
	public void testQueryingOnAndAfterDate() throws InstantiationException, IllegalAccessException {
		AbstractDateModelQueryTest.DateModelQueryTester.make(this).testQueryingForModelsOnAndAfterDate();
	}

	@Test
	public void testQueryingBeforeDate() throws InstantiationException, IllegalAccessException {
		AbstractDateModelQueryTest.DateModelQueryTester.make(this).testQueryingForModelsBeforeDate();
	}

	// MARK: Tester
	public static class DateModelQueryTester<T extends MutableDatedObjectifyModel<T>, Q extends AbstractDateModelQuery> extends DateQueryTester<T, Q>
	        implements DateQueryTesterDelegate<T, Q> {

		public static <T extends MutableDatedObjectifyModel<T>, Q extends AbstractDateModelQuery> DateModelQueryTester<T, Q> make(AbstractModelQueryTest<T, Q> modelQueryTest) {
			return new DateModelQueryTester<T, Q>(modelQueryTest);
		}

		public DateModelQueryTester(AbstractModelQueryTest<T, Q> modelQueryTest) {
			super(modelQueryTest);
			this.setDelegate(this);
		}

		// MARK: AbstractDateQueryTesterDelegate
		@Override
		public Date makeTestValue(int key) {
			return new Date(key * 1000);
		}

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
