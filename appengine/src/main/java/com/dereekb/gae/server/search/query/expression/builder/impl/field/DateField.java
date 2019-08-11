package com.dereekb.gae.server.search.query.expression.builder.impl.field;

import java.util.Calendar;
import java.util.Date;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.server.search.query.expression.builder.impl.AbstractSearchField;
import com.dereekb.gae.utilities.query.ExpressionOperator;

/**
 * Date expression
 *
 * @author dereekb
 *
 */
public class DateField extends AbstractSearchField {

	private static final String DATE_FIELD_FORMAT = "%s %s %d-%d-%d";

	private int day;
	private int month;
	private int year;
	private ExpressionOperator operator;

	public DateField(String name, Date date) {
		this(name, date, ExpressionOperator.EQUAL);
	}

	public DateField(String name, Date date, ExpressionOperator operator) {
		super(name);
		this.setDate(date);
		this.setOperator(operator);
	}

	public DateField(String name, int day, int month, int year, ExpressionOperator operator) {
		super(name);
		this.day = day;
		this.month = month;
		this.year = year;
		this.setOperator(operator);
	}

	public void setDate(int time) {
		Date date = new Date(time);
		this.setDate(date);
	}

	public void setDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		this.month = calendar.get(Calendar.MONTH) + 1;	// Month from 0 to 11?
		this.year = calendar.get(Calendar.YEAR);
	}

	public int getDay() {
		return this.day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public ExpressionOperator getOperator() {
		return this.operator;
	}

	public void setOperator(ExpressionOperator operator) {
		if (operator == null) {
			operator = ExpressionOperator.EQUAL;
		}

		this.operator = operator;
	}

	@Override
	public String getQueryExpression() {
		String operatorString = this.operator.toString();
		String queryString = String.format(DATE_FIELD_FORMAT, this.name, operatorString, this.year, this.month,
		        this.day);
		return queryString;
	}

	@Override
	public boolean isComplex() {
		return !this.operator.equals(ExpressionOperator.EQUAL);
	}

	@Override
	public SearchExpressionBuilder copyExpression() {
		return new DateField(this.name, this.day, this.month, this.year, this.operator);
	}

	@Override
	public String toString() {
		return "DateField [day=" + this.day + ", month=" + this.month + ", year=" + this.year + ", operator="
		        + this.operator + "]";
	}

}
