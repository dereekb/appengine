package com.dereekb.gae.server.search.query.expression.builder.impl;

import com.dereekb.gae.server.search.query.expression.SearchField;

/**
 * Abstract {@link SearchField}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractSearchField extends AbstractExpressionBuilderImpl
        implements SearchField {

	protected String name;

	public AbstractSearchField(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
