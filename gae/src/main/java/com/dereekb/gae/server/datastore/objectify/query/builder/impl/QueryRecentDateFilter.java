package com.dereekb.gae.server.datastore.objectify.query.builder.impl;

import java.util.Date;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.QueryFieldParameter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryConditionOperator;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryResultsOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.impl.ObjectifyQueryOrderingImpl;

/**
 * {@link QueryFieldParameter} extension that allows ordering query
 * results by date.
 * <p>
 * Can also provide a date and operator to do two searches.
 *
 * @author dereekb
 *
 */
public class QueryRecentDateFilter implements ObjectifyQueryRequestLimitedConfigurer {

	public static final String DEFAULT_DATE_FIELD = "date";

	private QueryFieldParameter<?> parameter;

	private String dateField;
	private ObjectifyQueryResultsOrdering dateOrdering = ObjectifyQueryResultsOrdering.Descending;

	private ObjectifyQueryConditionOperator dateOperator = ObjectifyQueryConditionOperator.LessOrEqualTo;
	private Date date = new Date();

	public QueryRecentDateFilter() {
		this(DEFAULT_DATE_FIELD);
	}

	public QueryRecentDateFilter(String dateField) {
		super();
		this.setDateField(dateField);
	}

	public QueryRecentDateFilter(QueryFieldParameter<?> parameter) {
		this.parameter = parameter;
	}

	public static QueryRecentDateFilter recentFieldEqualitySearch(QueryFieldParameter<?> parameter) {
		QueryRecentDateFilter filter = new QueryRecentDateFilter();
		filter.setParameter(parameter);
		return filter;
	}

	public QueryFieldParameter<?> getParameter() {
		return this.parameter;
	}

	public void setParameter(QueryFieldParameter<?> parameter) {
		this.parameter = parameter;
	}

	public String getDateField() {
		return this.dateField;
	}

	public void setDateField(String dateField) throws IllegalArgumentException {
		if (dateField == null) {
			throw new IllegalArgumentException("Date field name cannot be null.");
		}

		this.dateField = dateField;
	}

	public ObjectifyQueryResultsOrdering getDateOrdering() {
		return this.dateOrdering;
	}

	public void setDateOrdering(ObjectifyQueryResultsOrdering dateOrdering) {
		this.dateOrdering = dateOrdering;
	}

	public ObjectifyQueryConditionOperator getDateOperator() {
		return this.dateOperator;
	}

	public void setDateOperator(ObjectifyQueryConditionOperator dateOperator) {
		this.dateOperator = dateOperator;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// MARK: ObjectifyQueryRequestConfigurer
	@Override
	public void configure(ObjectifyQueryRequestLimitedBuilder request) {
		if (this.dateField != null && this.dateOperator != null) {
			ObjectifyConditionQueryFilter filter = new ObjectifyConditionQueryFilter(this.dateField, this.dateOperator,
			        this.date);
			request.addQueryFilter(filter);

			if (this.dateOrdering != null) {
				ObjectifyQueryOrderingImpl valueOrdering = new ObjectifyQueryOrderingImpl(this.dateField,
				        this.dateOrdering);
				request.addResultsOrdering(valueOrdering);
			}
		}

		this.parameter.configure(request);
	}

}
