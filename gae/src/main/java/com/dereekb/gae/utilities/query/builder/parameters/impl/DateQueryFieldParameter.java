package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Date;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * {@link AbstractQueryFieldParameter} used for {@link Date} query parameters.
 *
 * @author dereekb
 *
 */
public class DateQueryFieldParameter extends AbstractQueryFieldParameter<Date> {

	public static final String DEFAULT_DATE_FIELD = "date";

	public DateQueryFieldParameter(String field) {
		super(field, new Date());
	}

	public DateQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	public DateQueryFieldParameter(String field, Date value) {
		super(field, ExpressionOperator.LessOrEqualTo, value);
	}

	public DateQueryFieldParameter(String field, ExpressionOperator operator, Date value) {
		super(field, operator, value);
	}

	public static DateQueryFieldParameter recentDate() {
		return recentDate(DEFAULT_DATE_FIELD);
	}

	public static DateQueryFieldParameter recentDate(String field) {
		DateQueryFieldParameter parameter = new DateQueryFieldParameter(field);
		parameter.setOrdering(QueryResultsOrdering.Descending);
		parameter.setOperator(ExpressionOperator.LessOrEqualTo);
		return parameter;
	}

	@Override
	public AbstractQueryFieldParameter<Date> setValue(Date date) {
		if (date == null) {
			date = new Date();
		}

		return super.setValue(date);
	}

	// MARK: AbstractQueryFieldParameter
	@Override
	public String getParameterValue() {

		// TODO: Change to date format and not use {@link Long} as it is not
		// safe to use cross platform and does not contain timezone info.

		Long time = this.value.getTime();
		return time.toString();
	}

	@Override
	public void setParameterValue(String value) throws IllegalArgumentException {
		try {
			Long time = new Long(value);
			Date date = new Date(time);
			this.setValue(date);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Could not convert value to date.", e);
		}
	}

}
