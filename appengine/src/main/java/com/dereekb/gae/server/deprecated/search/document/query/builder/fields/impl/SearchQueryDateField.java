package com.dereekb.gae.server.search.query.builder.fields.impl;

import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.google.appengine.api.search.Field.FieldType;

/**
 * Date search field.
 *
 * @author dereekb
 *
 */
@Deprecated
public class SearchQueryDateField extends SearchQueryFieldImpl {

	private static final String DATE_FIELD_FORMAT = "%s %s %d-%d-%d";

	private final String field;
	private final Integer day;
	private final Integer month;
	private final Integer year;
	private final ExpressionOperator operator;

	/**
	 * Date defined as [Year, Month, Day];
	 */
	public SearchQueryDateField(String field, Integer[] date) {
		this(field, date[2], date[1], date[0], ExpressionOperator.EQUAL, false);
	}

	public SearchQueryDateField(String field, Integer day, Integer month, Integer year) {
		this(field, day, month, year, ExpressionOperator.EQUAL, false);
	}

	public SearchQueryDateField(String field, Integer day, Integer month, Integer year, Boolean not) {
		this(field, day, month, year, ExpressionOperator.EQUAL, not);
	}

	public SearchQueryDateField(String field, Integer day, Integer month, Integer year, ExpressionOperator operator) {
		this(field, day, month, year, operator, false);
	}

	public SearchQueryDateField(String field,
	        Integer day,
	        Integer month,
	        Integer year,
	        ExpressionOperator operator,
	        Boolean not) {
		super(FieldType.DATE, not);
		this.field = field;
		this.day = day;
		this.month = month;
		this.year = year;
		this.operator = operator;
	}

	@Override
	protected SearchQueryFieldImpl cloneQuery() {
		SearchQueryDateField clone = new SearchQueryDateField(this.field, this.day, this.month, this.year,
		        this.operator);

		return clone;
	}

	@Override
	protected String getQueryString() {
		String operatorString = this.operator.toString();
		String queryString = String.format(DATE_FIELD_FORMAT, this.field, operatorString, this.year, this.month,
		        this.day);

		return queryString;
	}
}
