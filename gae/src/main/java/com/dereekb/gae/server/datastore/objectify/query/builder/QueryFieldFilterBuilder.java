package com.dereekb.gae.server.datastore.objectify.query.builder;

import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryConditionOperator;


public class QueryFieldFilterBuilder {

	public ObjectifyConditionQueryFilter fieldFilter(String field,
	                                                 ObjectifyQueryConditionOperator operator,
	                                                 Object value) {
		ObjectifyConditionQueryFilter fieldFilter = new ObjectifyConditionQueryFilter(field, operator, value);
		return fieldFilter;
	}

}
