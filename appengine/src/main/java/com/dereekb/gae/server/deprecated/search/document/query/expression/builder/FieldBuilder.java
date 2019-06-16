package com.dereekb.gae.server.search.document.query.expression.builder;

import com.dereekb.gae.server.deprecated.search.document.query.expression.Field;

public interface FieldBuilder
        extends Field, ExpressionBuilder {

	public void setName(String name);

}
