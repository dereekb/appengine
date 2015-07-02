package com.dereekb.gae.server.datastore.objectify.query.builder;

import java.util.Date;
import java.util.List;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilterOperator;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryOrdering.QueryOrdering;

/**
 * Extension of {@link ObjectifyQueryBuilder} with components for Querying by a
 * creation date field, etc.
 *
 * @author dereekb
 */
public class ObjectifyDateQueryBuilder<T> extends ObjectifyQueryBuilder<T> {

	public static final String DEFAULT_DATE_FIELD = "date";

	private final String dateField;

	public ObjectifyDateQueryBuilder(Class<T> type) {
		this(type, DEFAULT_DATE_FIELD);
	}

	public ObjectifyDateQueryBuilder(Class<T> type, String dateField) throws IllegalArgumentException {
		super(type);

		if (this.dateField == null || this.dateField.isEmpty()) {
			throw new IllegalArgumentException("The date field cannot be empty or null.");
		}

		this.dateField = dateField;
	}

	public String getDateField() {
		return this.dateField;
	}

	public ObjectifyQuery<T> recentSearch() {
		return this.dateSearch(ObjectifyQueryFilterOperator.LessOrEqualTo, new Date());
	}

	public ObjectifyQuery<T> dateSearch(ObjectifyQueryFilterOperator operator,
	                                    Date date) {
		return this.fieldQuery(this.dateField, operator, date, QueryOrdering.Descending);
	}

	public ObjectifyQuery<T> recentFieldEqualitySearch(String field,
	                                                   Object value) {
		return this.recentFieldSearch(field, ObjectifyQueryFilterOperator.Equal, value, QueryOrdering.Ascending);
	}

	public ObjectifyQuery<T> recentFieldEqualitySearch(String field,
	                                                   Object value,
	                                                   QueryOrdering ordering) {
		return this.recentFieldSearch(field, ObjectifyQueryFilterOperator.Equal, value, ordering);
	}

	/**
	 * Search that adds the date field to the search, and sorts the results by
	 * newest created instead of ordered by key.
	 *
	 * Note that the field being searched against the date field must be defined
	 * in datastore-indexes.xml.
	 *
	 * https://groups.google.com/forum/#!topic/objectify-appengine/bHeNNpqGZCI
	 *
	 * @param field
	 *            Field name
	 * @param operator
	 * @param value
	 *            Value of the field
	 * @param ordering
	 *            The ordering here should match the one in
	 *            datastore-indexes.xml
	 * @return
	 */
	public ObjectifyQuery<T> recentFieldSearch(String field,
	                                           ObjectifyQueryFilterOperator operator,
	                                           Object value,
	                                           QueryOrdering ordering) {
		ObjectifyQuery<T> query = this.make();
		List<ObjectifyQueryFilter> filters = query.getQueryFilters();

		// First filter need to be the date filter.
		ObjectifyConditionQueryFilter recentFilter = new ObjectifyConditionQueryFilter(this.dateField,
		        ObjectifyQueryFilterOperator.LessOrEqualTo, new Date());
		filters.add(recentFilter);

		// Add next filter.
		ObjectifyConditionQueryFilter fieldFilter = new ObjectifyConditionQueryFilter(field, operator, value);
		filters.add(fieldFilter);

		// Order by Date
		ObjectifyQueryOrdering valueOrdering = new ObjectifyQueryOrdering(this.dateField, QueryOrdering.Descending);

		// Order then by the input type.
		if (ordering != null) {
			valueOrdering = valueOrdering.chain(field, ordering);
		}

		query.setResultsOrdering(valueOrdering);
		return query;
	}

}
