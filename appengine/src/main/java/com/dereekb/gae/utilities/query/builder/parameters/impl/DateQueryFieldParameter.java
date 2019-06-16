package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Date;

import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;
import com.dereekb.gae.utilities.time.DateUtility;
import com.dereekb.gae.utilities.time.IsoTimeConverter;
import com.dereekb.gae.utilities.time.impl.JavaTimeConverter;

/**
 * {@link AbstractQueryFieldParameter} used for {@link Date} query parameters.
 *
 * @author dereekb
 *
 */
public class DateQueryFieldParameter extends AbstractQueryFieldParameter<Date> {

	public static final String DEFAULT_DATE_FIELD = "date";

	private static final IsoTimeConverter DATE_CONVERTER = JavaTimeConverter.SINGLETON;

	/**
	 * New Query that returns for all dates.
	 *
	 * @param field
	 *            {@link String}. Never {@code null}.
	 */
	public DateQueryFieldParameter(String field) throws IllegalArgumentException {
		this(field, ExpressionOperator.GREATER_OR_EQUAL_TO);
	}

	public DateQueryFieldParameter(String field, ExpressionOperator operator) {
		super(field, operator, new Date(Long.MIN_VALUE));
	}

	public DateQueryFieldParameter(String field,
	        ExpressionOperator operator,
	        Date value,
	        QueryResultsOrdering ordering) {
		super(field, operator, value, ordering);
	}

	public DateQueryFieldParameter(String field, Date value) throws IllegalArgumentException {
		super(field, ExpressionOperator.GREATER_OR_EQUAL_TO, value);
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

	public static DateQueryFieldParameter make() {
		return new DateQueryFieldParameter(DEFAULT_DATE_FIELD);
	}

	/**
	 * Search originating from now in descending order.
	 */
	public static DateQueryFieldParameter recentDate() {
		return recentDate(DEFAULT_DATE_FIELD);
	}

	// MARK: Searching
	public static DateQueryFieldParameter searchNewestFirst(String field) {
		return searchByDates(field, QueryResultsOrdering.Descending);
	}

	public static DateQueryFieldParameter searchOldestFirst(String field) {
		return searchByDates(field, QueryResultsOrdering.Ascending);
	}

	public static DateQueryFieldParameter searchByDates(String field,
	                                                    QueryResultsOrdering ordering) {
		DateQueryFieldParameter parameter = new DateQueryFieldParameter(field);
		parameter.setOrdering(ordering);
		return parameter;
	}

	/**
	 * Search originating from now in descending order.
	 *
	 * @param field
	 *            Field. Never {@code null}.
	 * @return {@link DateQueryFieldParameter}. Never {@code null}.
	 */
	public static DateQueryFieldParameter recentDate(String field) {
		DateQueryFieldParameter parameter = new DateQueryFieldParameter(field, ExpressionOperator.LESS_OR_EQUAL_TO,
		        new Date());
		parameter.setOrdering(QueryResultsOrdering.Descending);
		return parameter;
	}

	// MARK: Sorting
	public static DateQueryFieldParameter sortByDatesDescending(String field) {
		return sortByDates(field, QueryResultsOrdering.Descending);
	}

	public static DateQueryFieldParameter sortByDatesAscending(String field) {
		return sortByDates(field, QueryResultsOrdering.Ascending);
	}

	public static DateQueryFieldParameter sortByDates(String field,
	                                                  QueryResultsOrdering ordering) {
		DateQueryFieldParameter parameter = new DateQueryFieldParameter(field);
		parameter.onlySort(ordering);
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
	public void setValue(Date date) {
		if (date == null) {
			date = new Date();
		}

		super.setValue(date);
	}

	// TODO: Move search range into more generic class.
	public void searchRange(Date startDate,
	                        Date endDate) {
		this.searchRange(startDate, endDate, QueryResultsOrdering.Ascending);
	}

	public void searchRange(Date startDate,
	                        Date endDate,
	                        QueryResultsOrdering ordering) {
		this.setPrimaryFilter(startDate, ExpressionOperator.GREATER_OR_EQUAL_TO);
		this.setSecondFilter(endDate, ExpressionOperator.LESS_OR_EQUAL_TO);
		this.setOrdering(ordering);
	}

	// MARK: AbstractQueryFieldParameter
	@Override
	protected String encodeParameterValue(Date date) {
		String dateString = DateUtility.getKeyForDate(date);

		if (dateString == null) {
			dateString = DATE_CONVERTER.convertToString(date);
		}

		return dateString;
	}

	@Override
	protected Date decodeParameterValue(String value) throws IllegalArgumentException {
		Date date = DateUtility.getDateForKey(value);

		if (date == null) {
			date = DATE_CONVERTER.convertFromString(value);
		}

		return date;
	}

}
