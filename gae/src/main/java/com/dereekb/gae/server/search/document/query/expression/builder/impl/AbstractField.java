package com.dereekb.gae.server.search.document.query.expression.builder.impl;



public abstract class AbstractField extends AbstractExpressionBuilderImpl {

	protected String name;

	public AbstractField(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
