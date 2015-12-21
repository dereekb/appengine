package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.AbstractTextField;

public class TextField extends AbstractTextField {

	private static final String TEXT_FIELD_FORMAT = "%s: %s%s";
	private static final String STEM_SYMBOL = "~";

	private boolean stemming;

	public TextField(String name, String value) {
		this(name, value, false);
	}

	public TextField(String name, String value, boolean stemming) {
		super(name, value);
		this.stemming = stemming;
	}

	public boolean isStemming() {
		return this.stemming;
	}

	public void setStemming(boolean stemming) {
		this.stemming = stemming;
	}

	@Override
	public String getExpressionValue() {
		String queryString = "";

		if (this.specificText || this.stemming == false) {
			queryString = super.getExpressionValue();
		} else {
			queryString = String.format(TEXT_FIELD_FORMAT, this.name, STEM_SYMBOL, this.value);
		}

		return queryString;
	}

	@Override
	public ExpressionBuilder copyExpression() {
		return new TextField(this.name, this.value, this.stemming);
	}

	@Override
	public String toString() {
		return "TextField [stemming=" + this.stemming + ", value=" + this.value + ", specificText=" + this.specificText
		        + ", name=" + this.name + "]";
	}

}
