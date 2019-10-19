package com.dereekb.gae.server.search.query.builder.fields.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.google.appengine.api.search.Field.FieldType;

/**
 * Number search field.
 *
 * @author dereekb
 *
 */
@Deprecated
public class SearchQueryNumberField extends SearchQueryFieldImpl {

	private static final String DOUBLE_FORMAT = "0.####E0";
	private static final String NUMBER_FIELD_FORMAT = "%s %s %s";

	private final Number value;
	private final String field;
	private final ExpressionOperator operator;

	public SearchQueryNumberField(String field, Number value) {
		this(field, ExpressionOperator.EQUAL, value, false);
	}

	public SearchQueryNumberField(String field, ExpressionOperator operator, Number value) {
		this(field, operator, value, false);
	}

	public SearchQueryNumberField(String field, ExpressionOperator operator, Number value, Boolean not) {
		super(FieldType.ATOM, not);
		this.value = value;
		this.field = field;
		this.operator = operator;
	}

	@Override
	protected SearchQueryFieldImpl cloneQuery() {
		SearchQueryNumberField clone = new SearchQueryNumberField(this.field, this.operator, this.value,
		        this.isNot());

		return clone;
	}

	@Override
	protected String getQueryString() {
		double d = this.value.doubleValue();

		NumberFormat formatter = new DecimalFormat(DOUBLE_FORMAT);
		String formattedDouble = formatter.format(d);
		String operatorString = this.operator.toString();

		String queryString = String.format(NUMBER_FIELD_FORMAT, this.field, operatorString, formattedDouble);
		return queryString;
	}
}
