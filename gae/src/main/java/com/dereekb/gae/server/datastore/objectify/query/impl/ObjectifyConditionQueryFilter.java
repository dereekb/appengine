package com.dereekb.gae.server.datastore.objectify.query.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.googlecode.objectify.cmd.Query;

/**
 * {@link ObjectifyQueryFilter} implementation for conditions.
 *
 * @author dereekb
 *
 */
public class ObjectifyConditionQueryFilter
        implements ObjectifyQueryFilter {

	private String field;
	private ExpressionOperator operator;
	private Object value;
	
	private transient String condition;

	public ObjectifyConditionQueryFilter(String field, ExpressionOperator operator, Object value) {
		if (operator == ExpressionOperator.IS_NULL) {
			operator = ExpressionOperator.EQUAL;
			value = null;
		}
		
		this.setField(field);
		this.setOperator(operator);
		this.setValue(value);
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		if (field == null) {
			throw new IllegalArgumentException("field cannot be null.");
		}

		this.field = field;
	}
	
	public ExpressionOperator getOperator() {
		return this.operator;
	}

	public void setOperator(ExpressionOperator operator) {
		if (operator == null) {
			throw new IllegalArgumentException("operator cannot be null.");
		}
	
		this.operator = operator;
	}

	public String getCondition() {
		if (this.condition == null) {
			this.condition = this.computeCondition();
		}
		
		return this.condition;
	}
	
	protected String computeCondition() {
		return this.operator.createFilter(this.field);
	}

	// MARK: ObjectifyQueryFilter
	@Override
	public boolean isInequality() {
		return this.operator.isInequality();
	}
	
	@Override
	public <T> Query<T> filter(Query<T> query) {
		Query<T> filteredQuery = query.filter(this.getCondition(), this.getValue());
		return filteredQuery;
	}

	@Override
	public String toString() {
		return "ObjectifyConditionQueryFilter [condition=" + this.condition + ", value=" + this.value + "]";
	}

}
