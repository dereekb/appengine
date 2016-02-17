package com.dereekb.gae.server.datastore.objectify.query.builder.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.AbstractQueryFieldParameter;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.DateQueryFieldParameter;

/**
 * {@link ObjectifyQueryRequestlimitedConfigurer} that wraps a
 * {@link DateQueryFieldParameter} and optional
 * {@link AbstractQueryFieldParameter}.
 *
 * @author dereekb
 *
 */
public class QueryRecentDateFilter implements ObjectifyQueryRequestLimitedConfigurer {

	public static final String DEFAULT_DATE_FIELD = "date";

	private DateQueryFieldParameter date;
	private AbstractQueryFieldParameter<?> parameter;

	public QueryRecentDateFilter() {
		this(null);
	}

	public QueryRecentDateFilter(AbstractQueryFieldParameter<?> parameter) {
		this(DEFAULT_DATE_FIELD, parameter);
	}

	public QueryRecentDateFilter(String dateField, AbstractQueryFieldParameter<?> parameter) {
		this.date = DateQueryFieldParameter.recentDate(dateField);
		this.setParameter(parameter);
	}

	public static QueryRecentDateFilter recentFieldEqualitySearch(AbstractQueryFieldParameter<?> parameter) {
		QueryRecentDateFilter filter = new QueryRecentDateFilter();
		filter.setParameter(parameter);
		return filter;
	}

	public DateQueryFieldParameter getDate() {
		return this.date;
	}

	public void setDate(DateQueryFieldParameter date) throws IllegalArgumentException {
		if (date == null) {
			throw new IllegalArgumentException("Date FieldParameter cannot be null.");
		}

		this.date = date;
	}

	public AbstractQueryFieldParameter<?> getParameter() {
		return this.parameter;
	}

	public void setParameter(AbstractQueryFieldParameter<?> parameter) {
		this.parameter = parameter;
	}

	// MARK: ObjectifyQueryRequestConfigurer
	@Override
	public void configure(ObjectifyQueryRequestLimitedBuilder request) {
		this.date.configure(request);

		if (this.parameter != null) {
			this.parameter.configure(request);
		}
	}

	@Override
	public String toString() {
		return "QueryRecentDateFilter [date=" + this.date + ", parameter=" + this.parameter + "]";
	}

}
