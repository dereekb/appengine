package com.dereekb.gae.server.search.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.server.search.query.expression.builder.impl.AbstractSearchField;
import com.dereekb.gae.utilities.query.ExpressionOperator;

public class NumberField extends AbstractSearchField {

	// private static final String DOUBLE_FORMAT = "0.#######E0";
	private static final String NUMBER_FIELD_FORMAT = "%s %s %s";

	private Number value;
	private ExpressionOperator operator;

	public NumberField(String name, Number value) {
		this(name, ExpressionOperator.EQUAL, value);
	}

	public NumberField(String name, ExpressionOperator operator, Number value) {
		super(name);
		this.setOperator(operator);
		this.setValue(value);
	}

	public Number getValue() {
		return this.value;
	}

	public void setValue(Number value) {
		this.value = value;
	}

	public ExpressionOperator getOperator() {
		return this.operator;
	}

	public void setOperator(ExpressionOperator operator) {
		this.operator = operator;
	}

	@Override
	public String getQueryExpression() {
		Double d = this.value.doubleValue();

		// NumberFormat formatter = new DecimalFormat(DOUBLE_FORMAT);
		String formattedDouble = d.toString(); // formatter.format(d);
		String operatorString = this.operator.toString();

		String queryString = String.format(NUMBER_FIELD_FORMAT, this.name, operatorString, formattedDouble);
		return queryString;
	}

	@Deprecated
	@Override
	public boolean isComplex() {
		return this.operator.isComplex();
	}

	@Override
	public SearchExpressionBuilder copyExpression() {
		return new NumberField(this.name, this.operator, this.value);
	}

	@Override
	public String toString() {
		return "NumberField [value=" + this.value + ", operator=" + this.operator + ", name=" + this.name + "]";
	}

}
