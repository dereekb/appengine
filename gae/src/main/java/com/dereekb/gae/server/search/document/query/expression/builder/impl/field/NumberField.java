package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.AbstractField;


public class NumberField extends AbstractField {

	private static final String DOUBLE_FORMAT = "0.####E0";
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
	public String getExpressionValue() {
		double d = this.value.doubleValue();

		NumberFormat formatter = new DecimalFormat(DOUBLE_FORMAT);
		String formattedDouble = formatter.format(d);
		String operatorString = this.operator.toString();

		String queryString = String.format(NUMBER_FIELD_FORMAT, this.name, operatorString, formattedDouble);
		return queryString;
	}

	@Override
	public boolean isComplex() {
		return this.operator.isComplex();
	}

	@Override
	public ExpressionBuilder copyExpression() {
		return new NumberField(this.name, this.operator, this.value);
	}

	@Override
    public String toString() {
		return "NumberField [value=" + this.value + ", operator=" + this.operator + ", name=" + this.name + "]";
    }

}
