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

	private String condition;
	private Object value;

	public ObjectifyConditionQueryFilter(String condition, Object value){
		this.setCondition(condition);
		this.setValue(value);
	}

	public ObjectifyConditionQueryFilter(String field,
			ExpressionOperator operator,
			Object value){
		this.setCondition(field, operator);
		this.setValue(value);
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String field,
	                         ExpressionOperator operator) throws IllegalArgumentException {
		if (field == null || operator == null) {
			throw new IllegalArgumentException("Field and operator cannot be null.");
		}

		String condition = operator.createFilter(field);
		this.setCondition(condition);
	}

	public void setCondition(String condition) throws IllegalArgumentException {
		if (condition == null) {
			throw new IllegalArgumentException("Condition cannot be null.");
		}

		this.condition = condition;
	}

	// MARK: ObjectifyQueryFilter
	@Override
	public <T> Query<T> filter(Query<T> query) {
		Query<T> filteredQuery = query.filter(this.condition, this.value);
		return filteredQuery;
	}

	@Override
	public String toString() {
		return "ObjectifyConditionQueryFilter [condition=" + this.condition + ", value=" + this.value + "]";
	}

}

