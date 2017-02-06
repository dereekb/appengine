package com.dereekb.gae.model.extension.search.document.search.model;

import java.util.Date;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.DateField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.NotExpression;

/**
 * Search model for a {@link Date}.
 *
 * @author dereekb
 *
 */
public class DateSearch {

	private static final String SPLITTER = ",";

	private boolean not = false;
	private Date date;
	private ExpressionOperator operator;

	public DateSearch() {
		this(new Date());
	}

	public DateSearch(Long date) {
		this(new Date(date));
	}

	public DateSearch(Date date) {
		this(date, ExpressionOperator.EQUAL);
	}

	public DateSearch(Date date, ExpressionOperator operator) {
		this(false, date, operator);
	}

	public DateSearch(Boolean not, Date date, ExpressionOperator operator) {
		this.setNot(not);
		this.setDate(date);
		this.setOperator(operator);
	}

	/**
	 * Creates a {@link DateSearch} from the input string.
	 * <p>
	 * Format: DATE(Integer),OP(String),NOT(Boolean)
	 *
	 *
	 * @param dateString
	 * @return {@link DateString} or {@code null} if nothing is input.
	 * @throws IllegalArgumentException
	 */
	public static DateSearch fromString(String dateString) throws IllegalArgumentException {
		DateSearch search = null;

		if (dateString != null && dateString.isEmpty() == false) {
			try {
				String[] split = dateString.split(SPLITTER);

				Boolean not = null;
				Long date = null;
				ExpressionOperator operator = null;

				switch (split.length) {
					default:
					case 3:
						not = new Boolean(split[2]);
					case 2:
						operator = ExpressionOperator.fromString(split[1]);
					case 1:
						date = new Long(split[0]);
						break;
				}

				search = new DateSearch(date);
				search.setOperator(operator);
				search.setNot(not);
			} catch (Exception e) {
				throw new IllegalArgumentException("Could not create date search.", e);
			}
		}

		return search;
	}

	public Boolean getNot() {
		return this.not;
	}

	public void setNot(Boolean not) {
		if (not == null) {
			not = false;
		}

		this.not = not;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ExpressionOperator getOperator() {
		return this.operator;
	}

	public void setOperator(ExpressionOperator operator) {
		this.operator = operator;
	}

	public ExpressionBuilder make(String field) {
		ExpressionBuilder builder = new DateField(field, this.date, this.operator);

		if (this.not) {
			builder = new NotExpression(builder);
		}

		return builder;
	}

	@Override
	public String toString() {
		return "DateSearch [not=" + this.not + ", date=" + this.date + ", operator=" + this.operator + "]";
	}

}
