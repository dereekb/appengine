package com.dereekb.gae.server.datastore.objectify.query.builder.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyDateQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.AbstractQueryFieldParameter;

/**
 * {@link ObjectifyQueryRequestlimitedConfigurer} that wraps a
 * {@link ObjectifyDateQueryFieldParameter} and optional
 * {@link AbstractQueryFieldParameter}.
 *
 * @author dereekb
 *
 */
public class QueryRecentDateFilter
        implements ObjectifyQueryRequestLimitedConfigurer {

	public static final String DEFAULT_DATE_FIELD = "date";

	private ObjectifyDateQueryFieldParameter date;
	private AbstractQueryFieldParameter<?> parameter;

	public QueryRecentDateFilter() {
		this(null);
	}

	public QueryRecentDateFilter(AbstractQueryFieldParameter<?> parameter) {
		this(DEFAULT_DATE_FIELD, parameter);
	}

	public QueryRecentDateFilter(String dateField, AbstractQueryFieldParameter<?> parameter) {
		this.date = ObjectifyDateQueryFieldParameter.recentDate(dateField);
		this.setParameter(parameter);
	}

	public static QueryRecentDateFilter recentFieldEqualitySearch(AbstractQueryFieldParameter<?> parameter) {
		QueryRecentDateFilter filter = new QueryRecentDateFilter();
		filter.setParameter(parameter);
		return filter;
	}

	public ObjectifyDateQueryFieldParameter getDate() {
		return this.date;
	}

	public void setDate(ObjectifyDateQueryFieldParameter date) throws IllegalArgumentException {
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
			ObjectifyAbstractQueryFieldParameter.configure(request, this.parameter);
		}
	}

	@Override
	public String toString() {
		return "QueryRecentDateFilter [date=" + this.date + ", parameter=" + this.parameter + "]";
	}

}
