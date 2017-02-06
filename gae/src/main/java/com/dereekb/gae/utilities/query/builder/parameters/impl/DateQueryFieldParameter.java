package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Date;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;
import com.dereekb.gae.utilities.time.IsoTimeConverter;
import com.dereekb.gae.utilities.time.impl.ThreeTenIsoTimeConverter;

/**
 * {@link AbstractQueryFieldParameter} used for {@link Date} query parameters.
 *
 * @author dereekb
 *
 */
public class DateQueryFieldParameter extends AbstractQueryFieldParameter<Date> {

	public static final String DEFAULT_DATE_FIELD = "date";

	private static final IsoTimeConverter DATE_CONVERTER = ThreeTenIsoTimeConverter.SINGLETON;

	/**
	 * New Query that searches for all dates before now.
	 * 
	 * @param field
	 *            {@link String}. Never {@code null}.
	 */
	public DateQueryFieldParameter(String field) throws IllegalArgumentException {
		this(field, ExpressionOperator.LESS_THAN);
	}

	public DateQueryFieldParameter(String field, ExpressionOperator operator) {
		super(field, operator, new Date());
	}

	public DateQueryFieldParameter(String field, Date value) throws IllegalArgumentException {
		super(field, ExpressionOperator.LESS_THAN, value);
	}

	public DateQueryFieldParameter(String field, ExpressionOperator operator, Date value) {
		super(field, operator, value);
	}

	public DateQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	public DateQueryFieldParameter(String field, AbstractQueryFieldParameter<Date> parameter)
	        throws IllegalArgumentException {
		super(field, parameter);
	}

	public DateQueryFieldParameter(AbstractQueryFieldParameter<Date> parameter) throws IllegalArgumentException {
		super(parameter);
	}

	public static DateQueryFieldParameter recentDate() {
		return recentDate(DEFAULT_DATE_FIELD);
	}

	public static DateQueryFieldParameter sortByDatesDescending(String field) {
		return sortByDates(field, QueryResultsOrdering.Descending);
	}

	public static DateQueryFieldParameter sortByDates(String field,
	                                                  QueryResultsOrdering ordering) {
		DateQueryFieldParameter parameter = new DateQueryFieldParameter(field);
		parameter.setOrdering(ordering);
		parameter.clearOperator();
		return parameter;
	}

	public static DateQueryFieldParameter recentDate(String field) {
		DateQueryFieldParameter parameter = new DateQueryFieldParameter(field);
		parameter.setOrdering(QueryResultsOrdering.Descending);
		parameter.setOperator(ExpressionOperator.LESS_OR_EQUAL_TO);
		return parameter;
	}

	public static DateQueryFieldParameter make(String field,
	                                           String parameterString)
	        throws IllegalArgumentException {
		DateQueryFieldParameter fieldParameter = null;

		if (parameterString != null) {
			fieldParameter = new DateQueryFieldParameter(field, parameterString);
		}

		return fieldParameter;
	}

	public static DateQueryFieldParameter make(String field,
	                                           DateQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		DateQueryFieldParameter fieldParameter = null;

		if (parameter != null) {
			fieldParameter = new DateQueryFieldParameter(field, parameter);
		}

		return fieldParameter;
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
		return DATE_CONVERTER.convertToString(this.getValue());
	}

	@Override
	public void setParameterValue(String value) throws IllegalArgumentException {
		this.setValue(DATE_CONVERTER.convertFromString(value));
	}

}