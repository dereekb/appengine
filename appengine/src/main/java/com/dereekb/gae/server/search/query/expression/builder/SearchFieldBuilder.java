package com.dereekb.gae.server.search.query.expression.builder;

import com.dereekb.gae.server.search.query.expression.SearchField;

public interface SearchFieldBuilder
        extends SearchField, SearchExpressionBuilder {

	public void setName(String name);

}
