package com.dereekb.gae.server.search.document.query.expression;


public interface Expression {

	public String getExpressionValue();

	public boolean isEmpty();

	public boolean isComplex();

}
