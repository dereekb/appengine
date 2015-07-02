package com.dereekb.gae.server.datastore.objectify.query;

import com.googlecode.objectify.cmd.Query;

public class ObjectifyConditionQueryFilter implements ObjectifyQueryFilter{
	
	private String condition;
	private Object value;
	
	public ObjectifyConditionQueryFilter(String condition, Object value){
		this.condition = condition;
		this.value = value;
	}
	
	public ObjectifyConditionQueryFilter(String field, 
			ObjectifyQueryFilterOperator operator, 
			Object value){
		
		this.condition = operator.createFilter(field);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public <T> Query<T> filter(Query<T> query) {
		Query<T> filteredQuery = query.filter(this.condition, this.value);
		return filteredQuery;
	}

	@Override
	public String toString() {
		return "ObjectifyConditionQueryFilter [condition=" + condition + ", value=" + value + "]";
	}
	
	
}

