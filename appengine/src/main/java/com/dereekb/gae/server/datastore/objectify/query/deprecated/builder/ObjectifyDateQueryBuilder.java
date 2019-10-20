package com.dereekb.gae.server.datastore.objectify.query.builder;

import java.util.Date;
import java.util.List;

import com.dereekb.gae.server.datastore.objectify.query.ConfiguredObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ExpressionOperator;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryOrderingImpl;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryOrderingImpl.QueryOrdering;

/**
 * Extension of {@link ObjectifyQueryBuilder} with components for Querying by a
 * creation date field, etc.
 *
 * @author dereekb
 */
public class ObjectifyDateQueryBuilder<T> extends ObjectifyQueryBuilder<T> {

	public static final String DEFAULT_DATE_FIELD = "date";

	private String dateField;

	public ObjectifyDateQueryBuilder(Class<T> type) {
		this(type, DEFAULT_DATE_FIELD);
	}

	public ObjectifyDateQueryBuilder(Class<T> type, String dateField) throws IllegalArgumentException {
		super(type);

		if (dateField == null || dateField.isEmpty()) {
			throw new IllegalArgumentException("The date field cannot be empty or null.");
		}

		this.dateField = dateField;
	}


	public String getDateField() {
		return this.dateField;
	}

	public void setDateField(String dateField) {
		this.dateField = dateField;
	}

	public ConfiguredObjectifyQuery<T> recentSearch() {
		return this.dateSearch(ExpressionOperator.LessOrEqualTo, new Date());
	}

	public ConfiguredObjectifyQuery<T> dateSearch(ExpressionOperator operator,
	                                    Date date) {
		return this.fieldQuery(this.dateField, operator, date, QueryOrdering.Descending);
	}

	public ConfiguredObjectifyQuery<T> recentFieldEqualitySearch(String field,
	                                                   Object value) {
		return this.recentFieldSearch(field, ExpressionOperator.Equal, value, QueryOrdering.Ascending);
	}

	public ConfiguredObjectifyQuery<T> recentFieldEqualitySearch(String field,
	                                                   Object value,
	                                                   QueryOrdering ordering) {
		return this.recentFieldSearch(field, ExpressionOperator.Equal, value, ordering);
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
	 *            index.yaml
	 * @return
	 */
	public ConfiguredObjectifyQuery<T> recentFieldSearch(String field,
	                                           ExpressionOperator operator,
	                                           Object value,
	                                           QueryOrdering ordering) {
		ConfiguredObjectifyQuery<T> query = this.make();
		List<ObjectifyQueryFilter> filters = query.getQueryFilters();

		// First filter need to be the date filter.
		ObjectifyConditionQueryFilter recentFilter = new ObjectifyConditionQueryFilter(this.dateField,
		        ExpressionOperator.LessOrEqualTo, new Date());
		filters.add(recentFilter);

		// Add next filter.
		ObjectifyConditionQueryFilter fieldFilter = new ObjectifyConditionQueryFilter(field, operator, value);
		filters.add(fieldFilter);

		// Order by Date
		ObjectifyQueryOrderingImpl valueOrdering = new ObjectifyQueryOrderingImpl(this.dateField, QueryOrdering.Descending);

		// Order then by the input type.
		if (ordering != null) {
			valueOrdering = valueOrdering.chain(field, ordering);
		}

		query.setResultsOrdering(valueOrdering);
		return query;
	}

}
