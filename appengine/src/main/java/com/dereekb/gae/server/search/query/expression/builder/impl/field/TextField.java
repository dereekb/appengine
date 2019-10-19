package com.dereekb.gae.server.search.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.server.search.query.expression.builder.impl.AbstractTextField;

/**
 * Arbitrary text field.
 *
 * @author dereekb
 *
 */
public class TextField extends AbstractTextField {

	private static final String STEMMED_TEXT_FIELD_FORMAT = "%s: ~%s";

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
	public String getQueryExpression() {
		String queryString;

		if (this.specificText || this.stemming == false) {
			queryString = super.getQueryExpression();
		} else {
			queryString = String.format(STEMMED_TEXT_FIELD_FORMAT, this.name, this.value);
		}

		return queryString;
	}

	@Override
	public SearchExpressionBuilder copyExpression() {
		return new TextField(this.name, this.value, this.stemming);
	}

	@Override
	public String toString() {
		return "TextField [stemming=" + this.stemming + ", value=" + this.value + ", specificText=" + this.specificText
		        + ", name=" + this.name + "]";
	}

}
