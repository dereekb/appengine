package com.dereekb.gae.server.search.query.expression.builder.impl.field;

import java.util.Date;

import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.dereekb.gae.utilities.time.DateUtility;

/**
 * {@link NumberField} for {@link Date} values that are stored rounded to the
 * nearest minute of their time stamp value.
 *
 * @author dereekb
 *
 */
public class TimeNumberField extends NumberField {

	public static final Long TIME_FIELD_ROUNDING = DateUtility.TIME_IN_MINUTE;	// Round to the nearest minute

	public TimeNumberField(String name, Date value) {
		this(name, TimeNumberField.toTimeNumber(value));
	}

	public TimeNumberField(String name, Date value, ExpressionOperator operator) {
		this(name, operator, TimeNumberField.toTimeNumber(value));
	}

	public TimeNumberField(String name, Number value) {
		super(name, value);
	}

	public TimeNumberField(String name, ExpressionOperator operator, Number value) {
		super(name, operator, value);
	}

	public static Double toTimeNumber(Date date) {
		Double timeNumber = null;

		if (date != null) {
			timeNumber = DateUtility.dateToRoundedTimeDouble(date, TIME_FIELD_ROUNDING);
		}

		return timeNumber;
	}

	@Override
	public String toString() {
		return "TimeNumberField [name=" + this.name + ", getValue()=" + this.getValue() + ", getOperator()="
		        + this.getOperator() + "]";
	}

}
